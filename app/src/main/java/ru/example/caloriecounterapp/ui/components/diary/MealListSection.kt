package ru.example.caloriecounterapp.ui.components.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import ru.example.caloriecounterapp.ui.theme.SurfaceColor
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@Composable
fun MealListSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сегодня",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            // TODO: Реализовать подсчет калорий по введенным продуктам
            Box(
                modifier = Modifier
                    .background(SurfaceColor, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "1800 ккал",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(17.dp))

        // Список карточек
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            MealItem(
                label = "Завтрак",
                cals = "350 ккал",
                iconRes = R.drawable.ic_breakfast,
                iconColor = Color(0xFFEEB21F)
            )
            MealItem(
                label = "Обед",
                cals = "450 ккал",
                iconRes = R.drawable.ic_lunch,
                iconColor = Color(0xFF97D452)
            )
            MealItem(
                modifier = Modifier.offset(y = (-5).dp),
                label = "Ужин",
                cals = "550 ккал",
                iconRes = R.drawable.ic_dinner,
                iconColor = Color(0xFF77A4D9)
            )
            MealItem(
                label = "Перекус",
                cals = "450 ккал",
                iconRes = R.drawable.ic_snack,
                iconColor = Color(0xFFF66767)
            )
        }
    }
}

@Composable
fun MealItem(
    modifier: Modifier = Modifier,
    label: String,
    cals: String,
    iconRes: Int,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка еды
            Icon(
                painter = painterResource(iconRes),
                contentDescription = label,
                tint = iconColor,
                modifier = modifier.size(38.dp)
            )

            Spacer(Modifier.width(16.dp))

            // Прием пищи
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            // Калории
            Text(
                text = cals,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(12.dp))

            // Иконка стрелочки
            Icon(
                painter = painterResource(R.drawable.ic_right_arrow),
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(backgroundColor = 0xFF121212, showBackground = true)
@Composable
fun MealListSectionPreview() {
    MealListSection()
}