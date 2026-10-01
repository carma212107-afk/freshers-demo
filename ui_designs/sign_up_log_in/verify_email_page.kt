import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerifyBackground = Color(0xFFF5FFF8)
private val VerifyPrimary = Color(0xFF0A5C2E)
private val VerifyAccent = Color(0xFF1A9E52)
private val VerifyProgressInactive = Color(0xFFB5DDC3)

@Composable
fun VerifyEmailPage(
	email: String = "<email@uni.ac.uk>",
	modifier: Modifier = Modifier,
	onResendEmail: () -> Unit = {},
) {
	Box(
		modifier = modifier
			.fillMaxSize()
			.background(VerifyBackground),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 30.dp, vertical = 100.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceBetween,
		) {
			VerifyProgress()

			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(26.dp),
			) {
				Text(
					text = "Check your email!",
					color = VerifyPrimary,
					fontSize = 35.sp,
					fontWeight = FontWeight.Black,
					lineHeight = 52.5.sp,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Carma is for students only.\nWe’ll verify your .ac.uk email instantly\nto confirm you’re enrolled",
					color = VerifyPrimary,
					fontSize = 16.sp,
					lineHeight = 24.sp,
					textAlign = TextAlign.Center,
				)
				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(0.dp),
				) {
					Text(
						text = "We’ve sent a verification link",
						color = VerifyPrimary,
						fontSize = 16.sp,
						lineHeight = 24.sp,
						textAlign = TextAlign.Center,
					)
					Row(
						horizontalArrangement = Arrangement.spacedBy(2.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text("to", color = VerifyPrimary, fontSize = 16.sp, lineHeight = 24.sp)
						Text(email, color = VerifyAccent, fontSize = 16.sp, lineHeight = 24.sp)
					}
				}
				Text(
					text = "Click the link to confirm you’re a student",
					color = VerifyPrimary,
					fontSize = 16.sp,
					lineHeight = 24.sp,
					textAlign = TextAlign.Center,
				)
			}

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 30.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
			) {
				Box(
					modifier = Modifier
						.width(121.dp)
						.height(40.dp),
					contentAlignment = Alignment.Center,
				) {
					Text(
						text = "Didn’t get it?",
						color = VerifyPrimary,
						fontSize = 20.sp,
						lineHeight = 30.sp,
						textAlign = TextAlign.Center,
					)
				}
				Box(
					modifier = Modifier
						.width(141.dp)
						.height(40.dp)
						.clip(RoundedCornerShape(20.dp))
						.background(VerifyPrimary)
						.clickable(role = Role.Button, onClick = onResendEmail),
					contentAlignment = Alignment.Center,
				) {
					Text(
						text = "Resend email",
						color = Color.White,
						fontSize = 16.sp,
						fontWeight = FontWeight.Medium,
					)
				}
			}
		}

		VerifyStatusBar(modifier = Modifier.align(Alignment.TopCenter))
		Box(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.navigationBarsPadding()
				.padding(bottom = 8.dp)
				.width(134.dp)
				.height(5.dp)
				.clip(CircleShape)
				.background(VerifyPrimary),
		)
	}
}

@Composable
private fun VerifyProgress() {
	Row(
		modifier = Modifier.semantics { contentDescription = "Step 5 of 5" },
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		repeat(4) {
			Box(Modifier.size(10.dp).clip(CircleShape).background(VerifyProgressInactive))
		}
		Box(
			Modifier
				.width(25.dp)
				.height(10.dp)
				.clip(CircleShape)
				.background(VerifyPrimary),
		)
	}
}

@Composable
private fun VerifyStatusBar(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(50.dp)
			.padding(horizontal = 30.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text("9:41", color = VerifyPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
		Canvas(
			modifier = Modifier
				.size(width = 67.dp, height = 12.dp)
				.semantics { contentDescription = "Signal, Wi-Fi, and battery status" },
		) {
			val unit = size.height / 12f
			for (bar in 0..3) {
				drawRoundRect(
					color = VerifyPrimary,
					topLeft = Offset(bar * 4.2f * unit, (12f - (bar + 2) * 2f) * unit),
					size = Size(2.6f * unit, (bar + 2) * 2f * unit),
					cornerRadius = CornerRadius(unit),
				)
			}
			drawArc(
				color = VerifyPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(20f * unit, -1f * unit),
				size = Size(12f * unit, 12f * unit),
				style = Stroke(width = 1.7f * unit, cap = StrokeCap.Round),
			)
			drawArc(
				color = VerifyPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(22.5f * unit, 2f * unit),
				size = Size(7f * unit, 7f * unit),
				style = Stroke(width = 1.5f * unit, cap = StrokeCap.Round),
			)
			drawCircle(VerifyPrimary, radius = unit, center = Offset(26f * unit, 10f * unit))
			val battery = Path().apply {
				moveTo(42f * unit, 2f * unit)
				lineTo(59f * unit, 2f * unit)
				quadraticTo(60f * unit, 2f * unit, 60f * unit, 3f * unit)
				lineTo(60f * unit, 9f * unit)
				quadraticTo(60f * unit, 10f * unit, 59f * unit, 10f * unit)
				lineTo(42f * unit, 10f * unit)
				quadraticTo(41f * unit, 10f * unit, 41f * unit, 9f * unit)
				lineTo(41f * unit, 3f * unit)
				quadraticTo(41f * unit, 2f * unit, 42f * unit, 2f * unit)
				close()
				moveTo(61.5f * unit, 4f * unit)
				lineTo(63f * unit, 4f * unit)
				lineTo(63f * unit, 8f * unit)
				lineTo(61.5f * unit, 8f * unit)
				close()
			}
			drawPath(battery, VerifyPrimary, style = Stroke(width = 1.2f * unit))
			drawRoundRect(
				color = VerifyPrimary,
				topLeft = Offset(43f * unit, 3f * unit),
				size = Size(14f * unit, 6f * unit),
				cornerRadius = CornerRadius(unit),
			)
		}
	}
}
