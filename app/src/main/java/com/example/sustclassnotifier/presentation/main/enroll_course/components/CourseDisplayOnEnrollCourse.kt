package com.example.sustclassnotifier.presentation.main.enroll_course.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.domain.event.EnrollCourseUIEvent
import com.example.sustclassnotifier.navigation.Screen
import com.example.sustclassnotifier.presentation.main.enroll_course.EnrollCourseViewModel
import com.example.sustclassnotifier.ui.theme.ExtraSmallHeight
import com.example.sustclassnotifier.ui.theme.LargeHeight
import com.example.sustclassnotifier.ui.theme.LargeRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun CourseDisplayOnEnrollCourse(
    course: Course,
    enrollCourseViewModel: EnrollCourseViewModel,
) {
    val isVisible = rememberSaveable { mutableStateOf(true) }
    val leave = SwipeAction(
        onSwipe = {
            val courseId = "${course.courseDepartment}:${course.courseCode}"
            enrollCourseViewModel.onEnrollCourseEvent(EnrollCourseUIEvent.LeaveCourseSwipe(courseId))
            isVisible.value = false
        },
        icon = {
            Icon(
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Red
    )

    val display = SwipeAction(
        onSwipe = {
            enrollCourseViewModel.onEnrollCourseEvent(EnrollCourseUIEvent.DisplayCourseSwipe(course, Screen.EnrollCourseScreen))
            isVisible.value = false
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.SmartDisplay,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Green
    )


    SwipeableActionsBox(
        startActions = listOf(display),
        endActions = listOf(leave),
        swipeThreshold = LargeHeight,
        modifier = Modifier
            .clip(LargeRounded)
            .fillMaxWidth()
            .height(NormalHeight)
            .padding(horizontal = MediumSpace),
    ) {
        AnimatedVisibility(
            visible = isVisible.value,
            exit = shrinkHorizontally()
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize(),
                shape = LargeRounded
            ) {
                Row(
                    modifier = Modifier
                        .clip(LargeRounded)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = course.courseDepartment)
                    Text(text = course.courseCode)
                    Text(text = course.courseTitle)
                    Text(text = course.courseCredit.toString())
                }
            }
        }
    }

}