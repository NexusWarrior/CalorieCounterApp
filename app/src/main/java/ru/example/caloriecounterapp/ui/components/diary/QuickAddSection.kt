package ru.example.caloriecounterapp.ui.components.diary

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import ru.example.caloriecounterapp.ui.theme.AccentLime
import ru.example.caloriecounterapp.ui.theme.SurfaceColor

@Composable
fun QuickAddSection(onScannerClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "Быстрое добавление",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickAddBtn(
                    label = "Продукт",
                    iconRes = R.drawable.ic_add_by_product,
                    modifier = Modifier.size(30.dp),
                    color = AccentLime,
                    onClick = { /* TODO */ }
                )
                QuickAddBtn(
                    label = "Блюдо",
                    iconRes = R.drawable.ic_add_by_dish,
                    modifier = Modifier.size(32.dp, 25.5.dp),
                    color = AccentLime,
                    onClick = { /* TODO */ }
                )
                QuickAddBtn(
                    label = "Сканер",
                    iconRes = R.drawable.ic_add_by_barcode,
                    modifier = Modifier.size(30.dp, 24.dp),
                    color = AccentLime,
                    onClick = onScannerClick
                )
            }
        }
    }
}

@Composable
fun QuickAddBtn(
    label: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(width = 76.dp, height = 41.dp)
                .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = label,
                tint = color,
                modifier = modifier
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun QuickAddSectionPreview() {
    QuickAddSection(onScannerClick = {})
}
