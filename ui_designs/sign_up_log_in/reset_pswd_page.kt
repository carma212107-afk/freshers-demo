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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ResetBackground = Color(0xFFF5FFF8)
private val ResetPrimary = Color(0xFF0A5C2E)
private val ResetAccent = Color(0xFF1A9E52)
private val ResetBorder = Color(0xFFB5DDC3)

@Composable
fun ResetPasswordPage(
	modifier: Modifier = Modifier,
	onResetPassword: (password: String, confirmation: String) -> Unit = { _, _ -> },
) {
	var password by remember { mutableStateOf("") }
	var confirmation by remember { mutableStateOf("") }
	val focusManager = LocalFocusManager.current

	Box(
		modifier = modifier
			.fillMaxSize()
			.background(ResetBackground),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(start = 30.dp, end = 30.dp, top = 100.dp, bottom = 45.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(30.dp),
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(5.dp),
			) {
				Text(
					text = "Reset password",
					color = ResetPrimary,
					fontSize = 30.sp,
					fontWeight = FontWeight.Black,
					lineHeight = 45.sp,
					textAlign = TextAlign.Center,
				)
				Text(
					text = "Passwords must contain\nboth upper and lowercase letters,\nat least 1 number,\nand at least 1 special character",
					color = ResetPrimary,
					fontSize = 16.sp,
					lineHeight = 24.sp,
					textAlign = TextAlign.Center,
				)
			}

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				ResetPasswordField(
					label = "Enter new password",
					value = password,
					onValueChange = { password = it },
				)
				ResetPasswordField(
					label = "Confirm password",
					value = confirmation,
					onValueChange = { confirmation = it },
				)
			}

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(42.dp)
					.clip(RoundedCornerShape(21.dp))
					.background(ResetPrimary)
					.clickable(role = Role.Button) {
						focusManager.clearFocus()
						onResetPassword(password, confirmation)
					},
				contentAlignment = Alignment.Center,
			) {
				Text(
					text = "Reset password",
					color = Color.White,
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
				)
			}
		}

		ResetStatusBar(modifier = Modifier.align(Alignment.TopCenter))
		Box(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.navigationBarsPadding()
				.padding(bottom = 8.dp)
				.width(134.dp)
				.height(5.dp)
				.clip(CircleShape)
				.background(ResetPrimary),
		)
	}
}

@Composable
private fun ResetPasswordField(
	label: String,
	value: String,
	onValueChange: (String) -> Unit,
) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(
			text = label,
			color = ResetPrimary,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			lineHeight = 30.sp,
		)
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			modifier = Modifier
				.fillMaxWidth()
				.height(40.dp)
				.clip(RoundedCornerShape(8.dp))
				.background(Color.White)
				.border(1.dp, ResetBorder, RoundedCornerShape(8.dp))
				.padding(horizontal = 15.dp),
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
			visualTransformation = PasswordVisualTransformation(),
			textStyle = TextStyle(
				color = ResetAccent,
				fontSize = 14.sp,
				lineHeight = 20.sp,
			),
			decorationBox = { innerTextField ->
				Box(contentAlignment = Alignment.CenterStart) {
					if (value.isEmpty()) {
						Text("Password", color = ResetAccent, fontSize = 14.sp, lineHeight = 20.sp)
					}
					innerTextField()
				}
			},
		)
	}
}

@Composable
private fun ResetStatusBar(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(50.dp)
			.padding(horizontal = 30.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text("9:41", color = ResetPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
		Canvas(Modifier.width(67.dp).height(12.dp)) {
			val unit = size.height / 12f
			for (bar in 0..3) {
				drawRoundRect(
					color = ResetPrimary,
					topLeft = Offset(bar * 4.2f * unit, (12f - (bar + 2) * 2f) * unit),
					size = Size(2.6f * unit, (bar + 2) * 2f * unit),
					cornerRadius = CornerRadius(unit),
				)
			}
			drawArc(
				color = ResetPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(20f * unit, -1f * unit),
				size = Size(12f * unit, 12f * unit),
				style = Stroke(width = 1.7f * unit, cap = StrokeCap.Round),
			)
			drawArc(
				color = ResetPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(22.5f * unit, 2f * unit),
				size = Size(7f * unit, 7f * unit),
				style = Stroke(width = 1.5f * unit, cap = StrokeCap.Round),
			)
			drawCircle(ResetPrimary, radius = unit, center = Offset(26f * unit, 10f * unit))
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
			drawPath(battery, ResetPrimary, style = Stroke(width = 1.2f * unit))
			drawRoundRect(
				color = ResetPrimary,
				topLeft = Offset(43f * unit, 3f * unit),
				size = Size(14f * unit, 6f * unit),
				cornerRadius = CornerRadius(unit),
			)
		}
	}
}
