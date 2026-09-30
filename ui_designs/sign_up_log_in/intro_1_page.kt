import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Intro1PassengersPage(
	modifier: Modifier = Modifier,
	onNext: () -> Unit = {},
	onSkipIntro: () -> Unit = {},
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 30.dp, vertical = 100.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceBetween,
		) {
			IntroStageIndicator()

			Box(
				modifier = Modifier
					.size(123.dp)
					.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp))
					.border(1.dp, Colours.LightMode.Primary, RoundedCornerShape(20.dp)),
			)

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(10.dp),
			) {
				Text(
					text = "Need a seat?\nWe’ve got you.",
					color = Colours.LightMode.Text,
					fontSize = 35.sp,
					fontWeight = FontWeight.Black,
					lineHeight = 40.25.sp,
				)
				Text(
					text = "Find students heading\nyour way and split the fuel cost.\nCheaper than any train, faster than any bus.",
					color = Colours.LightMode.Text,
					fontSize = 16.sp,
					lineHeight = 22.4.sp,
				)
			}

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(10.dp),
			) {
				IntroBenefit("75% cheaper than the train on most routes")
				IntroBenefit("Door-to-door: no stations, no connections")
				IntroBenefit("Save on emissions by reducing the number of vehicles on the road")
				IntroBenefit("Verified students only: everyone is ID checked")
				IntroBenefit("Female-only rides available for extra comfort")
			}

			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(8.dp),
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(43.dp)
						.background(Colours.DarkMode.Background1, RoundedCornerShape(20.dp))
						.clickable(onClick = onNext),
					contentAlignment = Alignment.Center,
				) {
					Text(
						text = "Next",
						color = Colours.DarkMode.Text,
						fontSize = 16.sp,
						fontWeight = FontWeight.Bold,
					)
				}
				Box(
					modifier = Modifier
						.width(117.dp)
						.height(43.dp)
						.clickable(onClick = onSkipIntro),
					contentAlignment = Alignment.Center,
				) {
					Text(
						text = "Skip intro",
						color = Colours.LightMode.Text,
						fontSize = 14.sp,
						fontWeight = FontWeight.Medium,
					)
				}
			}
		}

		IntroStatusBar(modifier = Modifier.align(Alignment.TopCenter))
		Box(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(bottom = 8.dp)
				.width(134.dp)
				.height(5.dp)
				.background(Colours.LightMode.Primary, RoundedCornerShape(100.dp)),
		)
	}
}

@Composable
private fun IntroStageIndicator() {
	Row(
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		repeat(5) { index ->
			if (index == 1) {
				Box(
					modifier = Modifier
						.width(25.dp)
						.height(10.dp)
						.background(Colours.LightMode.Primary, RoundedCornerShape(20.dp)),
				)
			} else {
				Box(
					modifier = Modifier
						.size(10.dp)
						.background(Colours.LightMode.Secondary, RoundedCornerShape(50)),
				)
			}
		}
	}
}

@Composable
private fun IntroBenefit(text: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.Top,
	) {
		Canvas(Modifier.padding(top = 8.dp).size(5.dp)) {
			drawCircle(Colours.LightMode.Text, radius = size.minDimension / 2f)
		}
		Text(
			text = text,
			color = Colours.LightMode.Text,
			fontSize = 15.sp,
			lineHeight = 21.sp,
		)
	}
}

@Composable
private fun IntroStatusBar(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier.fillMaxWidth().height(50.dp).padding(horizontal = 30.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text("9:41", color = Colours.LightMode.Primary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
		Canvas(Modifier.size(width = 67.dp, height = 12.dp)) {
			val iconColor = Colours.LightMode.Primary
			val bottom = size.height * 0.94f
			val barWidth = size.width * 0.055f
			val barGap = size.width * 0.035f
			for (index in 0..3) {
				val barHeight = size.height * (0.35f + index * 0.16f)
				val left = index * (barWidth + barGap)
				drawLine(iconColor, Offset(left, bottom), Offset(left, bottom - barHeight), barWidth, StrokeCap.Round)
			}
			val wifiLeft = size.width * 0.39f
			val wifiTop = size.height * 0.05f
			drawArc(iconColor, 215f, 110f, false, Offset(wifiLeft, wifiTop), androidx.compose.ui.geometry.Size(size.width * 0.2f, size.height * 1.6f), style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round))
			drawArc(iconColor, 215f, 110f, false, Offset(wifiLeft + size.width * 0.035f, wifiTop + size.height * 0.2f), androidx.compose.ui.geometry.Size(size.width * 0.13f, size.height * 1.1f), style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round))
			drawCircle(iconColor, radius = 1.1.dp.toPx(), center = Offset(size.width * 0.49f, size.height * 0.82f))
			val batteryLeft = size.width * 0.69f
			drawRoundRect(iconColor, Offset(batteryLeft, size.height * 0.12f), androidx.compose.ui.geometry.Size(size.width * 0.26f, size.height * 0.76f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.5.dp.toPx()), style = Stroke(width = 1.dp.toPx()))
			drawRoundRect(iconColor, Offset(batteryLeft + size.width * 0.025f, size.height * 0.24f), androidx.compose.ui.geometry.Size(size.width * 0.19f, size.height * 0.52f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx()))
			drawRect(iconColor, Offset(size.width * 0.96f, size.height * 0.34f), androidx.compose.ui.geometry.Size(size.width * 0.035f, size.height * 0.32f))
		}
	}
}
