package ru.example.caloriecounterapp.ui.components.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.R
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 19.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Дневник", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Сегодня, 12 мая", color = TextSecondary, fontSize = 16.sp)
        }
        IconButton(
//
            onClick = {/* TODO: Добавить открытие плашки с уведомлениями */ },
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.iconButtonColors()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview
@Composable
fun HeaderSectionPreview() {
    HeaderSection()
}