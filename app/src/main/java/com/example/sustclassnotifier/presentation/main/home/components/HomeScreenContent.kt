package com.example.sustclassnotifier.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sustclassnotifier.components.CustomElevatedButton
import com.example.sustclassnotifier.components.TwoTitleContainer
import com.example.sustclassnotifier.components.TwoTitleContainer2
import com.example.sustclassnotifier.data.model.User
import com.example.sustclassnotifier.domain.event.HomeUIEvent
import com.example.sustclassnotifier.navigation.TabItem
import com.example.sustclassnotifier.presentation.main.components.ClassMateTabRow
import com.example.sustclassnotifier.presentation.main.home.HomeViewModel
import com.example.sustclassnotifier.ui.theme.LargeSpace
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.SmallSpace

@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    user: User,
) {
    homeViewModel.getTodayAndTomorrowClassesClasses()
    val todayClasses by homeViewModel.todayClasses.collectAsState()
    val tomorrowClasses by homeViewModel.tomorrowClasses.collectAsState()

    val todayOrTomorrow = rememberSaveable { mutableStateOf(true) }

    val courses by homeViewModel.courses.collectAsState()
    val requestedCourses by homeViewModel.requestedCourses.collectAsState()

    val courseOrRequest = rememberSaveable { mutableStateOf(true) }

    val posts by homeViewModel.posts.collectAsState()
    val createPostDialogState by homeViewModel.createPostDialogState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ClassMateTabRow(tab = TabItem.Home)
        Spacer(modifier = Modifier.height(SmallSpace))
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .verticalScroll(
//                    rememberScrollState()
//                ),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(MediumSpace)
//        ) {
//
//            TwoTitleContainer2(
//                leftTitle = "Todays classes",
//                rightTitle ="" ,
//                leftClick = { /*TODO*/ },
//                rightClick = { /*TODO*/ })
//            Row (
//                modifier = Modifier.background(Color.Green)
//            ){
//
//                Text(text = "Course code - ")
//                Text(text = " Start time - ")
//                Text(text = "End time -")
//                Text(text = "Place - ")
//                Text(text = "Active")
//            }
//
////            TwoTitleContainer(
////                leftTitle = "Todays Classes",
////                rightTitle = "",
////                leftClick = { todayOrTomorrow.value = true },
////                rightClick = { todayOrTomorrow.value = false }
////            )
//
//            FixedHeightLazyColumn(items = if (todayOrTomorrow.value) todayClasses else tomorrowClasses) {
//                ClassDisplay(
//                    homeViewModel = homeViewModel,
//                    classDetails = it,
//                    user = user
//                )
//            }
//
//            Spacer(modifier =
//                Modifier.height(100.dp)
//            )

            TwoTitleContainer(
                leftTitle = "Registerd Courses",
                rightTitle = "Pending Courses",
                leftClick = {
                    homeViewModel.getCourseList(user.courses)
                    courseOrRequest.value = true
                },
                rightClick = {
                    homeViewModel.getRequestedCourseList(user.requestedCourses)
                    courseOrRequest.value = false
                }
            )
            Row (
                modifier = Modifier.background(Color.Green)
                    .fillMaxWidth(.9f)
                , horizontalArrangement = Arrangement.SpaceEvenly
            ){

                Text(text = "Course code  ")
                Text(text = " Course Title  ")
                Text(text = "Credit")

            }

            FixedHeightSwipeAbleLazyColumn(items = if (courseOrRequest.value) courses else requestedCourses,
                key = {
                    "${it.courseDepartment}:${it.courseCode}"
                }) {
                CourseDisplay(
                    course = it,
                    homeViewModel = homeViewModel,
                    isRequested = requestedCourses.contains(it)
                )
            }

//            Spacer(modifier = Modifier.height(LargeSpace))
//            CustomElevatedButton(text = "Create Post", onClick = { homeViewModel.onHomeEvent(HomeUIEvent.PostButtonClicked) })
//            Spacer(modifier = Modifier.height(MediumSpace))
//            posts.forEach {
//                PostDisplay(post = it, homeViewModel = homeViewModel, isCreator = it.creator == user.email)
//            }
//
//        }
//
//        if (createPostDialogState) {
//            CreatePostDialog(homeViewModel = homeViewModel, user = user)
//        }
        }
    }
