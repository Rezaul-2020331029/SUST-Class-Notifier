package com.example.sustclassnotifier.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sustclassnotifier.core.GetModelFromDocument.getCourseFromFirestoreDocument
import com.example.sustclassnotifier.data.model.ClassDetails
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.domain.repository.CourseRepository
import com.example.sustclassnotifier.domain.state.DataState
import com.example.sustclassnotifier.strings.ACTIVE_STATUS
import com.example.sustclassnotifier.strings.ASSIGNMENTS_COLLECTION
import com.example.sustclassnotifier.strings.CLASSES_COLLECTION
import com.example.sustclassnotifier.strings.COURSES
import com.example.sustclassnotifier.strings.COURSES_COLLECTION
import com.example.sustclassnotifier.strings.COURSE_CREATOR
import com.example.sustclassnotifier.strings.PENDING_STATUS
import com.example.sustclassnotifier.strings.REQUESTED_COURSES
import com.example.sustclassnotifier.strings.TERM_TESTS_COLLECTION
import com.example.sustclassnotifier.strings.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val firestoreRef: FirebaseFirestore,
) : CourseRepository {

    private val usersCollection = firestoreRef.collection(USERS_COLLECTION)
    private val coursesCollection = firestoreRef.collection(COURSES_COLLECTION)
    private val classesCollection = firestoreRef.collection(CLASSES_COLLECTION)
    private val termTestsCollection = firestoreRef.collection(TERM_TESTS_COLLECTION)
    private val assignmentsCollection = firestoreRef.collection(ASSIGNMENTS_COLLECTION)

    override fun createCourse(
        course: Course,
        classDetailsSet: Set<ClassDetails>,
    ): Flow<Pair<DataState<Course>, DataState<List<ClassDetails>>>> = flow {
        emit(Pair(DataState.Loading, DataState.Loading))
        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Send course Delete request
        coursesCollection.document(courseId).get().addOnSuccessListener { courseSnapshot ->
            if (courseSnapshot.exists() && courseSnapshot != null) {
                val creatorEmail = courseSnapshot[COURSE_CREATOR].toString()
                usersCollection.document(creatorEmail).get().addOnSuccessListener { creator ->
                    if (creator.exists() && creator != null) {
                        val requestedCourses = if (creator[REQUESTED_COURSES] == null) creator[REQUESTED_COURSES] as MutableList<String>  else mutableListOf()
                        requestedCourses.add(courseId)
                        usersCollection.document(creatorEmail).update(REQUESTED_COURSES,requestedCourses).addOnFailureListener {
                            Log.d(TAG, "createCourse: request update: ${it.localizedMessage}")
                        }.addOnFailureListener {
//                            Log.d(TAG, "createCourse: ${it.localizedMessage}")
                            Log.d(TAG, "createCourse: ${it.localizedMessage}")
                        }
                    }
                }.addOnFailureListener {
                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()


        // Send Teacher request
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener { creator ->
            if (creator.exists() && creator != null) {
                val requestedCourses = if (creator[REQUESTED_COURSES] != null)  creator[REQUESTED_COURSES] as MutableList<String> else  mutableListOf()
                requestedCourses.add(courseId)
                usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES,requestedCourses).addOnFailureListener {
//                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()

        // Update Creators Course List
        usersCollection.document(course.courseCreator).get().addOnSuccessListener { creator ->
            if (creator.exists() && creator != null) {
                val creatorCourses = if (creator[COURSES] != null) creator[COURSES] as MutableList<String>  else mutableListOf()
                creatorCourses.add(courseId)
                usersCollection.document(course.courseCreator).update(COURSES,creatorCourses).addOnFailureListener {
//                    Log.d(TAG, "createCourse: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }.addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()

        // Create Course Classes
        classDetailsSet.forEach { classDetails ->
            val classIndex = classDetailsSet.indexOf(classDetails)
            val classId = "$courseId:$classIndex"
            val details = classDetails.copy(classNo = classIndex, classCourseCode = course.courseCode, classDepartment = course.courseDepartment)
//            Log.d(TAG, "createCourse: $details")
            classesCollection.document(classId).set(details.toMap()).addOnFailureListener {
//                Log.d(TAG, "createCourse: ${it.localizedMessage}")
                return@addOnFailureListener
            }.await()
        }

        
        // Create Course
        coursesCollection.document(courseId).set(course.toMap()).addOnFailureListener {
//            Log.d(TAG, "createCourse: ${it.localizedMessage}")
            return@addOnFailureListener
        }.await()
        
        emit(Pair(DataState.Success(course), DataState.Success(classDetailsSet.toList())))

    }.catch {
        Log.d(TAG, "createCourse: ${it.localizedMessage}")
    }




    override fun getRequestedCourses(courseIds: List<String>): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (course.pendingStatus && document.id.contains(it)) {
                        pendingCourses.add(course)
                    }
                }
                trySend(pendingCourses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getCourseList: ${it.localizedMessage}")
    }

    override fun getCreatedCourses(
        courseIds: List<String>,
        creatorEmail: String,
    ): Flow<List<Course>> = callbackFlow {

        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
                courseIds.forEach {
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it) && document[COURSE_CREATOR] == creatorEmail) {
                        pendingCourses.add(course)
                    }
                }
                trySend(pendingCourses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getCourseList: ${it.localizedMessage}")
    }

    override fun getPendingCourseList(courseIds: List<String>): Flow<List<Course>> = callbackFlow {
//        Log.d(TAG, "getPendingCourseList: $courseIds")
        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val pendingCourses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
//                Log.d(TAG, "getPendingCourseList: ${document.data}")
                courseIds.forEach {
//                    Log.d(TAG, "getPendingCourseList: $it, ${document.id}")
                    val course = getCourseFromFirestoreDocument(document)
                    if (course.pendingStatus && document.id.contains(it)) {
                        pendingCourses.add(course)
//                        Log.d(TAG, "getPendingCourseList: true")
                    }
                }
                trySend(pendingCourses).isSuccess
            }
            
            if (error != null) {
                Log.d(TAG, "getPendingCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getPendingCourseList: $it")
    }

    override fun getCourses(courseIds: List<String>): Flow<List<Course>> = callbackFlow {
//        Log.d(TAG, "getPendingCourseList: $courseIds")
        val snapshotListener = coursesCollection.addSnapshotListener { value, error ->
            val courses = mutableListOf<Course>()
            value?.documents?.forEach { document ->
//                Log.d(TAG, "getPendingCourseList: ${document.data}")
                courseIds.forEach {
//                    Log.d(TAG, "getPendingCourseList: $it, ${document.id}")
                    val course = getCourseFromFirestoreDocument(document)
                    if (!course.pendingStatus && document.id.contains(it)) {
                        courses.add(course)
//                        Log.d(TAG, "getPendingCourseList: true")
                    }
                }
                trySend(courses).isSuccess
            }

            if (error != null) {
                Log.d(TAG, "getPendingCourseList: ${error.localizedMessage}")
            }
        }

        awaitClose {
            snapshotListener.remove()
        }

    }.catch {
//        Log.d(TAG, "getPendingCourseList: $it")
    }


    override fun acceptCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Change Teacher's request status
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener {
            if (it.exists() && it != null) {
                val requestedCourses = if (it[REQUESTED_COURSES] != null) it[REQUESTED_COURSES] as MutableList<String> else mutableListOf()

                requestedCourses.remove(courseId)
                usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                    return@addOnFailureListener
                }
            }
        }.await()

        usersCollection.document(course.courseTeacher).get().addOnSuccessListener {
            if (it.exists() && it != null) {
                val courses = if (it[COURSES] != null) it[COURSES] as MutableList<String> else mutableListOf()
                courses.add(courseId)

                usersCollection.document(course.courseTeacher).update(COURSES, courses).addOnFailureListener {
                    return@addOnFailureListener
                }
            }
        }.await()

        // Change course pending status
        coursesCollection.document(courseId).update(PENDING_STATUS, false).addOnSuccessListener {
            return@addOnSuccessListener
        }.await()

    }.catch {
        Log.d(TAG, "acceptCourse: ${it.localizedMessage}")
    }

    override fun deleteCourse(course: Course): Flow<DataState<Course>> = flow<DataState<Course>> {
        emit(DataState.Loading)

        val courseId = "${course.courseDepartment}:${course.courseCode}"

        // Delete the course from teacher
        usersCollection.document(course.courseTeacher).get().addOnSuccessListener { courseIds ->
            val requestedCourses = if (courseIds[REQUESTED_COURSES] != null) courseIds[REQUESTED_COURSES] as MutableList<String> else mutableListOf()

            requestedCourses.remove(courseId)
            usersCollection.document(course.courseTeacher).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                Log.d(TAG, "deleteCourse from teacher: ${it.localizedMessage}")
                return@addOnFailureListener
            }
        }.await()


        // Delete the course from creator
        usersCollection.document(course.courseCreator).get().addOnSuccessListener { courseIds ->
            val courses = if (courseIds[COURSES]  != null) courseIds[COURSES] as MutableList<String> else mutableListOf()
            courses.remove(courseId)
            usersCollection.document(course.courseCreator).update(COURSES, course).addOnFailureListener {
                Log.d(TAG, "deleteCourse from creator: ${it.localizedMessage}")
                return@addOnFailureListener
            }
        }.await()

        usersCollection.document(course.courseCreator).get().addOnSuccessListener { courseIds ->
            val requestedCourses = if (courseIds[REQUESTED_COURSES] != null) courseIds[REQUESTED_COURSES] as MutableList<String> else mutableListOf()
            requestedCourses.remove(courseId)
            usersCollection.document(course.courseCreator).update(REQUESTED_COURSES, requestedCourses).addOnFailureListener {
                Log.d(TAG, "deleteCourse from creator: ${it.localizedMessage}")
                return@addOnFailureListener
            }
        }.await()

        // Delete the course from enrolled students
        course.enrolledStudents.forEach { student ->
            usersCollection.document(student).get().addOnSuccessListener { courseIds ->
                val courses = if (courseIds[COURSES]  != null) courseIds[COURSES] as MutableList<String> else mutableListOf()
                courses.remove(courseId)

                usersCollection.document(student).update(COURSES, course).addOnFailureListener {
                    Log.d(TAG, "deleteCourse from students: ${it.localizedMessage}")
                    return@addOnFailureListener
                }
            }
        }


        // Delete the course classes
        classesCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    classesCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }.await()

        // Delete the course term tests
        termTestsCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    termTestsCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }.await()

        // Delete the course assignments
        assignmentsCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot?.forEach { queryDocumentSnapshot ->
                if (queryDocumentSnapshot != null && queryDocumentSnapshot.id.contains(courseId)) {
                    assignmentsCollection.document(queryDocumentSnapshot.id).delete().addOnFailureListener {
                        Log.d(TAG, "deleteCourse classes: ${it.localizedMessage}")
                        return@addOnFailureListener
                    }
                }
            }
        }.await()

        /*TODO: Consider deleting course related posts */

        // Delete the course
        coursesCollection.document(courseId).delete().addOnFailureListener {
            Log.d(TAG, "deleteCourse: ${it.localizedMessage}")
        }.await()


    }.catch {
        Log.d(TAG, "deleteCourse: catch block: ${it.localizedMessage}")
    }

    override fun enrollCourse(courseId: String, email: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        usersCollection.document(email).get().addOnSuccessListener { user ->
            if (user.exists() && user != null) {
                val alreadyEnrolled = if (user[COURSES] != null) user[COURSES] as List<String> else listOf()
                val set = mutableSetOf<String>()
                alreadyEnrolled.forEach {
                    set.add(it)
                }
                set.add(courseId)
                Log.d(TAG, "enrollCourse: $email : $set")
                usersCollection.document(email).update(COURSES, set.toList()).addOnFailureListener {
                    Log.d(TAG, "enrollCourse: ${it.localizedMessage}")
                }
            }
        }.await()
        emit(DataState.Success(true))

    }.catch {
        emit(DataState.Error(it.localizedMessage!!))
        Log.d(TAG, "enrollCourse: ${it.localizedMessage}")
    }

    override fun leaveCourse(courseId: String, email: String): Flow<DataState<Boolean>> = flow<DataState<Boolean>> {
        emit(DataState.Loading)
        usersCollection.document(email).get().addOnSuccessListener { user ->
            if (user.exists() && user != null) {
                val alreadyEnrolled = if (user[COURSES] != null) user[COURSES] as MutableList<String> else mutableListOf()
                alreadyEnrolled.remove(courseId)
                usersCollection.document(email).update(COURSES, alreadyEnrolled).addOnFailureListener {
                    Log.d(TAG, "leaveCourse: ${it.localizedMessage}")
                }
            }
        }.await()

        emit(DataState.Success(true))

    }.catch {
        Log.d(TAG, "leaveCourse: ${it.localizedMessage}")
    }

    override fun updateClassStatus(details: ClassDetails): Flow<DataState<ClassDetails>> = flow {

        emit(DataState.Loading)
        val classId = "${details.classDepartment}:${details.classCourseCode}:${details.classNo}"
        classesCollection.document(classId).update(ACTIVE_STATUS, details.isActive).addOnFailureListener {
            Log.d(TAG, "updateClassStatus: ${it.localizedMessage}")
        }
        emit(DataState.Success(details))
    }.catch {
        emit(DataState.Error(it.localizedMessage!!))
        Log.d(TAG, "updateClassStatus: ${it.localizedMessage}")
    }

    companion object {
        const val TAG = "CourseRepositoryImpl"
    }

}