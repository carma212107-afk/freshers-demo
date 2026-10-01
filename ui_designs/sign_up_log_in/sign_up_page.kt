import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.text.TextDecoration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SignUpBackground = Color(0xFFF5FFF8)
private val SignUpPrimary = Color(0xFF0A5C2E)
private val SignUpAccent = Color(0xFF1A9E52)
private val SignUpBorder = Color(0xFFB5DDC3)
private val SignUpSelected = Color(0xFFB5DDC3)

private enum class SignUpRole { Driver, Passenger }

@Composable
fun SignUpPage(
	modifier: Modifier = Modifier,
	onCreateAccount: (name: String, email: String, isDriver: Boolean) -> Unit = { _, _, _ -> },
	onTermsOfService: () -> Unit = {},
	onPrivacyPolicy: () -> Unit = {},
) {
	var name by remember { mutableStateOf("") }
	var email by remember { mutableStateOf("") }
	var role by remember { mutableStateOf(SignUpRole.Driver) }
	val focusManager = LocalFocusManager.current

	Box(
		modifier = modifier
			.fillMaxSize()
			.background(SignUpBackground),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(start = 30.dp, end = 30.dp, top = 100.dp, bottom = 200.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			SignUpProgress()
			Spacer(Modifier.height(20.dp))

			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Text(
					text = "Join Carma.",
					color = SignUpPrimary,
					fontSize = 30.sp,
					fontWeight = FontWeight.Black,
					lineHeight = 36.sp,
				)
				Text(
					text = "Students only - verified instantly.",
					color = SignUpPrimary,
					fontSize = 16.sp,
					lineHeight = 24.sp,
				)
			}
			Spacer(Modifier.height(18.dp))

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				SignUpField(label = "Your name", value = name, placeholder = "Name", onValueChange = { name = it })
				SignUpField(
					label = "University email",
					value = email,
					placeholder = "email@university.ac.uk",
					keyboardType = KeyboardType.Email,
					onValueChange = { email = it },
				)
			}
			Spacer(Modifier.height(18.dp))

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(6.dp),
			) {
				Text(
					text = "I am a...",
					color = SignUpPrimary,
					fontSize = 20.sp,
					fontWeight = FontWeight.Medium,
					lineHeight = 30.sp,
				)
				Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
					RoleOption(
						label = "Driver",
						selected = role == SignUpRole.Driver,
						onClick = { role = SignUpRole.Driver },
						modifier = Modifier.weight(1f),
					) {
						CarIcon(color = if (role == SignUpRole.Driver) SignUpPrimary else SignUpAccent)
					}
					RoleOption(
						label = "Passenger",
						selected = role == SignUpRole.Passenger,
						onClick = { role = SignUpRole.Passenger },
						modifier = Modifier.weight(1f),
					) {
						BagsIcon(color = if (role == SignUpRole.Passenger) SignUpPrimary else SignUpAccent)
					}
				}
			}
			Spacer(Modifier.height(16.dp))

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(42.dp)
					.clip(RoundedCornerShape(21.dp))
					.background(SignUpPrimary)
					.clickable {
						focusManager.clearFocus()
						onCreateAccount(name, email, role == SignUpRole.Driver)
					},
				contentAlignment = Alignment.Center,
			) {
				Text(
					text = "Create my account",
					color = Color.White,
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
				)
			}
			Spacer(Modifier.height(18.dp))

			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = "By creating an account, you agree to our",
					color = SignUpAccent,
					fontSize = 15.sp,
					textAlign = TextAlign.Center,
					lineHeight = 22.5.sp,
				)
				Row(
					horizontalArrangement = Arrangement.spacedBy(4.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					PolicyLink("Terms of Service", onTermsOfService)
					Text("and", color = SignUpAccent, fontSize = 15.sp)
					PolicyLink("Privacy Policy", onPrivacyPolicy)
				}
				Spacer(Modifier.height(10.dp))
				Text(
					text = "Students only.",
					color = SignUpAccent,
					fontSize = 15.sp,
					textAlign = TextAlign.Center,
				)
			}
		}

		SignUpStatusBar(modifier = Modifier.align(Alignment.TopCenter))
		Box(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.navigationBarsPadding()
				.padding(bottom = 8.dp)
				.width(134.dp)
				.height(5.dp)
				.clip(CircleShape)
				.background(SignUpPrimary),
		)
	}
}

@Composable
private fun SignUpField(
	label: String,
	value: String,
	placeholder: String,
	keyboardType: KeyboardType = KeyboardType.Text,
	onValueChange: (String) -> Unit,
) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(5.dp),
	) {
		Text(
			text = label,
			color = SignUpPrimary,
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
				.border(1.dp, SignUpBorder, RoundedCornerShape(8.dp))
				.padding(horizontal = 15.dp),
			singleLine = true,
			keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
			textStyle = TextStyle(
				color = SignUpAccent,
				fontSize = 14.sp,
				lineHeight = 20.sp,
			),
			decorationBox = { innerTextField ->
				Box(contentAlignment = Alignment.CenterStart) {
					if (value.isEmpty()) {
						Text(placeholder, color = SignUpAccent, fontSize = 14.sp, lineHeight = 20.sp)
					}
					innerTextField()
				}
			},
		)
	}
}

@Composable
private fun RoleOption(
	label: String,
	selected: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	icon: @Composable () -> Unit,
) {
	val shape = RoundedCornerShape(15.dp)
	Column(
		modifier = modifier
			.height(85.dp)
			.clip(shape)
			.background(if (selected) SignUpSelected else Color.White)
			.border(1.dp, if (selected) SignUpAccent else SignUpBorder, shape)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				role = Role.RadioButton,
				onClick = onClick,
			)
			.semantics { this.selected = selected },
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		icon()
		Spacer(Modifier.height(10.dp))
		Text(
			text = label,
			color = if (selected) SignUpPrimary else SignUpAccent,
			fontSize = 16.sp,
			fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
		)
	}
}

@Composable
private fun CarIcon(color: Color) {
	Canvas(
		modifier = Modifier
			.size(width = 42.dp, height = 21.dp)
			.semantics { contentDescription = "Car" },
	) {
		scale(size.width / 42f, size.height / 21f, pivot = Offset.Zero) {
			val car = Path().apply {
				moveTo(3f, 13f)
				lineTo(7f, 13f)
				lineTo(14f, 6f)
				quadraticTo(15f, 5f, 17f, 5f)
				lineTo(29f, 5f)
				quadraticTo(31f, 5f, 32f, 7f)
				lineTo(35f, 12f)
				lineTo(39f, 12f)
				lineTo(40f, 13f)
				lineTo(40f, 18f)
				lineTo(35f, 18f)
				quadraticTo(34f, 14f, 30f, 14f)
				quadraticTo(26f, 14f, 25f, 18f)
				lineTo(16f, 18f)
				quadraticTo(15f, 14f, 11f, 14f)
				quadraticTo(7f, 14f, 6f, 18f)
				lineTo(3f, 18f)
				close()
			}
			drawPath(car, color, style = Stroke(width = 1.8f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round))
			drawCircle(color, radius = 2.2f, center = Offset(11f, 18f))
			drawCircle(color, radius = 2.2f, center = Offset(30f, 18f))
		}
	}
}

@Composable
private fun BagsIcon(color: Color) {
	Canvas(
		modifier = Modifier
			.size(width = 21.dp, height = 26.dp)
			.semantics { contentDescription = "Luggage" },
	) {
		scale(size.width / 21f, size.height / 26f, pivot = Offset.Zero) {
			val stroke = Stroke(width = 1.8f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
			val bag = Path().apply {
				moveTo(3f, 11f)
				lineTo(13f, 11f)
				lineTo(13f, 24f)
				lineTo(3f, 24f)
				close()
				moveTo(6f, 11f)
				lineTo(6f, 8f)
				quadraticTo(6f, 6f, 8f, 6f)
				lineTo(10f, 6f)
				quadraticTo(12f, 6f, 12f, 8f)
				lineTo(12f, 11f)
			}
			drawPath(bag, color, style = stroke)
			val secondBag = Path().apply {
				moveTo(11f, 15f)
				lineTo(18f, 15f)
				lineTo(18f, 24f)
				lineTo(11f, 24f)
				moveTo(13f, 15f)
				lineTo(13f, 13f)
				quadraticTo(13f, 12f, 14f, 12f)
				lineTo(16f, 12f)
				quadraticTo(17f, 12f, 17f, 13f)
				lineTo(17f, 15f)
			}
			drawPath(secondBag, color, style = stroke)
		}
	}
}

@Composable
private fun SignUpProgress() {
	Row(
		modifier = Modifier.semantics { contentDescription = "Step 4 of 5" },
		horizontalArrangement = Arrangement.spacedBy(5.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		repeat(3) {
			Box(Modifier.size(10.dp).clip(CircleShape).background(SignUpBorder))
		}
		Box(
			Modifier
				.width(25.dp)
				.height(10.dp)
				.clip(CircleShape)
				.background(SignUpPrimary),
		)
		Box(Modifier.size(10.dp).clip(CircleShape).background(SignUpBorder))
	}
}

@Composable
private fun PolicyLink(text: String, onClick: () -> Unit) {
	Text(
		text = text,
		modifier = Modifier.clickable(onClick = onClick),
		color = SignUpPrimary,
		fontSize = 15.sp,
		textDecoration = TextDecoration.Underline,
	)
}

@Composable
private fun SignUpStatusBar(modifier: Modifier = Modifier) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(50.dp)
			.padding(horizontal = 30.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text("9:41", color = SignUpPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
		Canvas(Modifier.size(width = 67.dp, height = 12.dp)) {
			val unit = size.height / 12f
			for (bar in 0..3) {
				drawRoundRect(
					color = SignUpPrimary,
					topLeft = Offset(bar * 4.2f * unit, (12f - (bar + 2) * 2f) * unit),
					size = Size(2.6f * unit, (bar + 2) * 2f * unit),
					cornerRadius = androidx.compose.ui.geometry.CornerRadius(unit),
				)
			}
			drawArc(
				color = SignUpPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(20f * unit, -1f * unit),
				size = Size(12f * unit, 12f * unit),
				style = Stroke(width = 1.7f * unit, cap = StrokeCap.Round),
			)
			drawArc(
				color = SignUpPrimary,
				startAngle = 220f,
				sweepAngle = 100f,
				useCenter = false,
				topLeft = Offset(22.5f * unit, 2f * unit),
				size = Size(7f * unit, 7f * unit),
				style = Stroke(width = 1.5f * unit, cap = StrokeCap.Round),
			)
			drawCircle(SignUpPrimary, radius = unit, center = Offset(26f * unit, 10f * unit))
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
			drawPath(battery, SignUpPrimary, style = Stroke(width = 1.2f * unit))
			drawRoundRect(
				color = SignUpPrimary,
				topLeft = Offset(43f * unit, 3f * unit),
				size = Size(14f * unit, 6f * unit),
				cornerRadius = androidx.compose.ui.geometry.CornerRadius(unit),
			)
		}
	}
}
