package com.example.cse225ca1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardApp()
        }
    }
}
@Composable
fun DashboardApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(navController)
        }
        composable("profile") {
            SimpleScreen("Profile Screen")
        }
        composable("settings") {
            SimpleScreen("Settings Screen")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    val topics = listOf("Android", "ML", "Web", "DSA", "Cloud", "AI")
    val courses = listOf(
        Course("Android Basics", "Learn Jetpack Compose", "New"),
        Course("Machine Learning", "Intro to ML concepts", "In Progress"),
        Course("Web Dev", "HTML, CSS, JS", "New"),
        Course("DSA", "Data Structures & Algorithms", "In Progress")
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Profile") },
                            onClick = {
                                expanded = false
                                navController.navigate("profile")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                expanded = false
                                navController.navigate("settings")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔹 Topics Section
            item {
                Text("Topics", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                ) {
                    topics.forEach { topic ->
                        TopicChip(topic)
                    }
                }
            }

            // 🔹 Courses Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Courses", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(courses) { course ->
                CourseCard(course)
            }
        }
    }
}
@Composable
fun TopicChip(text: String) {
    AssistChip(
        onClick = { },
        label = { Text(text) },
        modifier = Modifier
            .padding(end = 8.dp)
    )
}
@Composable
fun CourseCard(course: Course) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(course.title, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(4.dp))

            Text(course.description, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            StatusBadge(course.status)
        }
    }
}
@Composable
fun StatusBadge(status: String) {

    val color = when (status) {
        "New" -> Color(0xFF4CAF50)
        "In Progress" -> Color(0xFFFF9800)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(status, color = color)
    }
}

@Composable
fun SimpleScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.headlineMedium)
    }
}