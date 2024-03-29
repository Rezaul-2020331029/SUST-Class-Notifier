package com.example.sustclassnotifier.presentation.main.home.components

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.unit.dp
import com.example.sustclassnotifier.data.model.Course
import com.example.sustclassnotifier.domain.event.HomeUIEvent
import com.example.sustclassnotifier.navigation.Screen
import com.example.sustclassnotifier.presentation.main.home.HomeViewModel
import com.example.sustclassnotifier.ui.theme.ExtraSmallHeight
import com.example.sustclassnotifier.ui.theme.LargeHeight
import com.example.sustclassnotifier.ui.theme.LargeRounded
import com.example.sustclassnotifier.ui.theme.MediumSpace
import com.example.sustclassnotifier.ui.theme.NormalHeight
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun CourseDisplay(
    course: Course,
    homeViewModel: HomeViewModel,
    isRequested: Boolean,
) {
    val isVisible = rememberSaveable { mutableStateOf(true) }

    val accept = SwipeAction(
        onSwipe = {
            homeViewModel.onHomeEvent(HomeUIEvent.AcceptCourseRequest(course))
            isVisible.value = false
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Green
    )

    val delete = SwipeAction(
        onSwipe = {
            homeViewModel.onHomeEvent(HomeUIEvent.DeleteCourseRequest(course))
            isVisible.value = false
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(ExtraSmallHeight)
            )
        },
        background = Color.Red
    )

    val display = SwipeAction(
        onSwipe = {
            homeViewModel.onHomeEvent(HomeUIEvent.DisplayCourse(course, Screen.HomeScreen))
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
        background = Color.Cyan
    )

    if (isRequested) {
        SwipeableActionsBox(
            startActions = listOf(accept),
            endActions = listOf(delete),
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

                        Text(text = course.courseCode)
                        Text(text = course.courseTitle)
                        Text(text = course.courseCredit.toString())
                    }
                }
            }
        }
    } else if (isVisible.value) {
        SwipeableActionsBox(
            startActions = listOf(display),
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
                        .height(100.dp)
                    ,
                    shape = LargeRounded
                ) {
                    Row(
                        modifier = Modifier
                            .clip(LargeRounded)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                       // Text(text = course.courseDepartment)
                        Text(text = course.courseCode)
                        Text(text = course.courseTitle)
                        Text(text = course.courseCredit.toString())
                    }
                }
            }
        }
    }
}