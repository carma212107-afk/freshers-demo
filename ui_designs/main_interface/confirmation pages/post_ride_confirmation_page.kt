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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostRideConfirmationPage(
	modifier: Modifier = Modifier,
	startCity: String = "<startC>",
	endCity: String = "<endC>",
	date: String = "<date>",
	departureTime: String = "HH:mm",
	seatsAvailable: Int = 2,
	filters: List<String> = listOf("<filter>", "<filter>", "<filter>"),
	onViewMyListing: () -> Unit = {},
	onBackToHome: () -> Unit = {},
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(horizontal = 30.dp, vertical = 100.dp)
				.padding(bottom = 100.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(35.dp),
		) {
			ConfirmationCheckmark()

			Column(
				modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(5.dp),
			) {
				Text(
					text = "Ride posted!",
					color = Colours.LightMode.Text,
					fontSize = 35.sp,
					fontWeight = FontWeight.Black,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Your ride is now live.",
					color = Colours.LightMode.Text,
					fontSize = 16.sp,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Students on this route will be notified and can book a seat instantly.",
					color = Colours.LightMode.Text,
					fontSize = 16.sp,
					textAlign = TextAlign.Center,
				)
			}

			RideConfirmationInfo(
				startCity = startCity,
				endCity = endCity,
				date = date,
				departureTime = departureTime,
				seatsAvailable = seatsAvailable,
				filters = filters,
			)

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(5.dp),
			) {
				ConfirmationButton(
					label = "View my listing",
					filled = true,
					onClick = onViewMyListing,
				)
				ConfirmationButton(
					label = "Back to home",
					filled = false,
					onClick = onBackToHome,
				)
			}
		}

		ConfirmationStatusBar(modifier = Modifier.align(Alignment.TopCenter))
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
private fun ConfirmationCheckmark() {
	Canvas(Modifier.size(100.dp)) {
		val iconColor = Colours.Accent
		drawCircle(
			color = iconColor,
			radius = size.minDimension * 0.44f,
			style = Stroke(width = 5.dp.toPx()),
		)
		val check = Path().apply {
			moveTo(size.width * 0.29f, size.height * 0.51f)
			lineTo(size.width * 0.44f, size.height * 0.66f)
			lineTo(size.width * 0.73f, size.height * 0.36f)
		}
		drawPath(
			path = check,
			color = iconColor,
			style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round),
		)
	}
}

@Composable
private fun RideConfirmationInfo(
	startCity: String,
	endCity: String,
	date: String,
	departureTime: String,
	seatsAvailable: Int,
	filters: List<String>,
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.border(1.dp, Colours.LightMode.Border, RoundedCornerShape(15.dp))
			.background(Colours.LightMode.Background2, RoundedCornerShape(15.dp))
			.padding(horizontal = 15.dp, vertical = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(15.dp),
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(10.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(startCity, color = Colours.LightMode.Text, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
			Text("-->", color = Colours.Accent, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
			Text(endCity, color = Colours.LightMode.Text, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
		}

		Row(
			horizontalArrangement = Arrangement.spacedBy(10.dp),
		) {
			RideInfoValue(value = date, label = "date")
			RideInfoValue(value = departureTime, label = "departure")
			RideInfoValue(value = "$seatsAvailable left", label = "seats available")
		}

		Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
			filters.take(2).takeIf { it.isNotEmpty() }?.let { labels ->
				Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
					labels.forEach { FilterLabel(it) }
				}
			}
			filters.drop(2).takeIf { it.isNotEmpty() }?.let { labels ->
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
				) {
					labels.forEach { FilterLabel(it) }
				}
			}
		}
	}
}

@Composable
private fun RideInfoValue(value: String, label: String) {
	Column(
		modifier = Modifier
			.background(Colours.LightMode.Secondary, RoundedCornerShape(5.dp))
			.padding(horizontal = 10.dp, vertical = 5.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(
			text = value,
			color = Colours.LightMode.Text,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center,
			maxLines = 1,
		)
		Text(
			text = label,
			color = Colours.LightMode.Text,
			fontSize = 15.sp,
			textAlign = TextAlign.Center,
			maxLines = 1,
		)
	}
}

@Composable
private fun FilterLabel(label: String) {
	Text(
		text = label,
		modifier = Modifier
			.background(Colours.LightMode.Secondary, RoundedCornerShape(20.dp))
			.padding(horizontal = 12.dp, vertical = 3.dp),
		color = Colours.LightMode.Text,
		fontSize = 12.sp,
		fontWeight = FontWeight.Bold,
		textAlign = TextAlign.Center,
		maxLines = 1,
	)
}

@Composable
private fun ConfirmationButton(label: String, filled: Boolean, onClick: () -> Unit) {
	val background = if (filled) Colours.DarkMode.Background1 else Colours.LightMode.Background2
	val text = if (filled) Colours.DarkMode.Text else Colours.LightMode.Text
	val border = if (filled) Colours.DarkMode.Background1 else Colours.LightMode.Primary

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(44.dp)
			.background(background, RoundedCornerShape(20.dp))
			.border(1.dp, border, RoundedCornerShape(20.dp))
			.clickable(onClick = onClick),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = label,
			color = text,
			fontSize = 16.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center,
		)
	}
}

@Composable
private fun ConfirmationStatusBar(modifier: Modifier = Modifier) {
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
