package ru.example.caloriecounterapp.ui.components.diary

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import ru.example.caloriecounterapp.R
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.DarkBackground
import ru.example.caloriecounterapp.ui.theme.SurfaceColor
import ru.example.caloriecounterapp.ui.theme.TextSecondary

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalorieStatsCard() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isWideScreen = screenWidth > 600.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (isWideScreen) 32.dp else 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Адаптивный размер кольца
        val ringSize = min(screenWidth * 0.5f, 180.dp)
        CalorieRing(modifier = Modifier.size(ringSize))

        Spacer(Modifier.height(24.dp))

        // Используем FlowRow для адаптивности карточек БЖУ
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = if (isWideScreen) 3 else 2
        ) {
            val itemModifier =
                if (isWideScreen) Modifier.weight(1f) else Modifier
                    .widthIn(min = 140.dp)
                    .weight(1f)

            CompactNutritionCard(
                title = "Белки",
                value = "95 / 120",
                progress = 0.56f,
                color = AccentLime,
                modifier = itemModifier
            )

            CompactNutritionCard(
                title = "Жиры",
                value = "50 / 60",
                progress = 0.3f,
                color = Color(0xFFFFC82E),
                modifier = itemModifier
            )

            CompactNutritionCard(
                title = "Углеводы",
                value = "210 / 250",
                progress = 0.74f,
                color = Color(0xFFB35DFF),
                modifier = itemModifier
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(if (isWideScreen) 0.5f else 1f),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SurfaceColor
            )
        ) {
            Text("Подробнее", color = TextSecondary)

            Spacer(Modifier.width(8.dp))

            Icon(
                painterResource(R.drawable.ic_right_arrow),
                null,
                tint = TextSecondary
            )
        }
    }
}


@Composable
fun CalorieRing(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        val progress = 0.75f
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        ) {
            val strokeWidth = size.minDimension * 0.08f
            drawArc(
                color = Color.White.copy(alpha = 0.1f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = AccentLime,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Калории", color = TextSecondary, fontSize = 14.sp)
            Text(
                "1800",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text("/ 2200 ккал", color = TextSecondary, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))
            Text("Осталось", color = TextSecondary, fontSize = 11.sp)
            Text(
                "400 ккал",
                color = AccentLime,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MacroBar(label: String, current: String, total: String, progress: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Text(label, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(current, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(4.dp))
                Text(total, color = TextSecondary, fontSize = 15.sp)
            }
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(50))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight() // Заполняет всю высоту родительского Box
                        .background(color, RoundedCornerShape(50))
                )
            }
        }
    }
}

@Composable
fun NutritionCard(
    title: String,
    current: Int,
    total: Int,
    progressColor: Color
) {
    val progress = current.toFloat() / total.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceColor)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                ) {
                    append("$current")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFB8C1CC),
                        fontSize = 18.sp
                    )
                ) {
                    append(" / $total г")
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF5B6470))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(progressColor)
            )
        }
    }
}

@Composable
fun NutritionScreen() {
    Column(
        modifier = Modifier
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        NutritionCard(
            title = "Белки",
            current = 95,
            total = 120,
            progressColor = Color(0xFF9BE34A)
        )

        NutritionCard(
            title = "Жиры",
            current = 50,
            total = 60,
            progressColor = Color(0xFFFFC82E)
        )

        NutritionCard(
            title = "Углеводы",
            current = 210,
            total = 250,
            progressColor = Color(0xFFB35DFF)
        )
    }
}

@Preview
@Composable
fun CalorieStatsCardPreview() {
    CalorieStatsCard()
}

@Preview
@Composable
fun PreviewNutrition() {
    NutritionScreen()
}