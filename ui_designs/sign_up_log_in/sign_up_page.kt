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

private val SignUpPrimary = Color(0xFF0A5C2E)
private val Colours.LightMode.Border = Color(0xFFB5DDC3)
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
			.background(Colours.LightMode.Background1),
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(start = 30.dp, end = 30.dp, top = 100.dp, bottom = 200.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			StageIndicator(currentStage = 4, noStages = 5)
			Spacer(Modifier.height(20.dp))

			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Text(
					text = "Join Carma.",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Heading1.size,
					fontWeight = TextFormatting.Heading1.weight
				)
				Text(
					text = "Students only - verified instantly.",
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Text1.size,
                    fontWeight = TextFormatting.Text1.weight
				)
			}
			Spacer(Modifier.height(18.dp))

			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				InputField(label = "Your name", value = name, placeholder = "Name", onValueChange = { name = it })
				InputField(
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
					color = Colours.LightMode.Text,
					fontSize = TextFormatting.Text2.size,
					fontWeight = TextFormatting.Text2.weight
				)
				Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
					RoleOption(
						label = "Driver",
						selected = role == SignUpRole.Driver,
						onClick = { role = SignUpRole.Driver },
						modifier = Modifier.weight(1f),
					) {
						carIcon(colour = if (role == SignUpRole.Driver) Colours.Buttons.Selected.icon else Colours.Buttons.Unselected.icon)
					}
					RoleOption(
						label = "Passenger",
						selected = role == SignUpRole.Passenger,
						onClick = { role = SignUpRole.Passenger },
						modifier = Modifier.weight(1f),
					) {
						bagsIcon(colour = if (role == SignUpRole.Passenger) Colours.Buttons.Selected.icon else Colours.Buttons.Unselected.icon)
					}
				}
			}
			Spacer(Modifier.height(16.dp))

            IntroContinueButtons(continueLabel = "Create my account", onContinue = onCreateAccount)

			Spacer(Modifier.height(18.dp))

			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = "By creating an account, you agree to our",
					color = Colours.Accent,
					fontSize = TextFormatting.Text3.size,
                    fontWeight = TextFormatting.Text3.weight,
					textAlign = TextAlign.Center,
				)
				Row(
					horizontalArrangement = Arrangement.spacedBy(4.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					PolicyLink("Terms of Service", onTermsOfService)
					Text("and", color = Colours.Accent, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
					PolicyLink("Privacy Policy", onPrivacyPolicy)
				}
				Spacer(Modifier.height(10.dp))
				Text(
					text = "Students only.",
					color = Colours.Accent,
					fontSize = TextFormatting.Text3.size,
					fontWeight = TextFormatting.Text3.weight,
					textAlign = TextAlign.Center,
				)
			}
		}
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
			.background(if (selected) Colours.Buttons.Selected.background else Colours.Buttons.Unselected.background)
			.border(1.dp, if (selected) Colours.Buttons.Selected.border else Colours.Buttons.Unselected.border, shape)
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
			color = if (selected) Colours.Buttons.Selected.text else Colours.Buttons.Unselected.text,
			fontSize = if (selected) TextFormatting.Button1.size else TextFormatting.Button2.size,
			fontWeight = if (selected) TextFormatting.Button1.weight else TextFormatting.Button2.weight,
		)
	}
}

@Composable
private fun PolicyLink(text: String, onClick: () -> Unit) {
	Text(
		text = text,
		modifier = Modifier.clickable(onClick = onClick),
		color = Colours.LightMode.Text,
		fontSize = TextFormatting.Text3.size,
		fontWeight = TextFormatting.Text3.weight,
		textDecoration = TextDecoration.Underline,
	)
}