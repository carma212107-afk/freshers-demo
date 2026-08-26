object Colours {
  val Accent: Color = Color(0xFF1A9E52)

    // Light mode colors
  val LightModeBackground1: Color = Color(0xFFF5FFF8)
  val LightModeBackground2: Color = Color(0xFFFFFFFF)
  val LightModePrimary: Color = Color(0xFF0A5C2E)
  val LightModeSecondary: Color = Color(0xFFB5DDC3)
  val LightModeBorder: Color = Color(0xFFB5DDC3)
  val LightModeText: Color = Color(0xFF0A5C2E)
  val LightModeInputText: Color = Color(0xFF1A9E52)

    // Dark mode colors
  val DarkModeBackground1: Color = Color(0xFF0A5C2E)
  val DarkModeBackground2: Color = Color(0xFF2A7149)
  val DarkModePrimary: Color = Color(0xFFF5FFF8)
  val DarkModeSecondary: Color = Color(0xFFB5DDC3)
  val DarkModeBorder: Color = Color(0xFFF5FFF8)
  val DarkModeText: Color = Color(0xFFFFFFFF)
  val DarkModeInputText: Color = Color(0xFFFFFFFF)

    // Misc colours
  val RedBackground: Color = Color(0xFFE49E99)
}
data class ColourScheme(
  val background: Color,
  val border: Color,
  val text: Color,
  val icon: Color,
)


object Dimensions {
  val currentUIX = 402.dp
  val currentUIY = 874.dp
}


data class Format(
  val weight: FontWeight,
  val size: TextUnit
)
object TextFormatting {
  val MenuBarTitle = Format(FontWeight.Black, 30.sp)
  val IntroTitle = Format(FontWeight.Black, 50.sp)
  val Heading1 = Format(FontWeight.Black, 35.sp) // Title
  val Heading2 = Format(FontWeight.ExtraBold, 25.sp) // Section title
  val Text1 = Format(FontWeight.Normal, 16.sp) // Title description
  val Text2 = Format(FontWeight.Bold, 20.sp) // Key info
  val Text3 = Format(FontWeight.Normal, 15.sp) // Text description
  val SmallText1 = Format(FontWeight.Bold, 12.sp) // Key info
  val SmallText2 = Format(FontWeight.Normal, 12.sp) // Text description
  val Boxes1 = Format(FontWeight.Bold, 15.sp) // Key info
  val Boxes2 = Format(FontWeight.Light, 14.sp) // Description
  val Figures1 = Format(FontWeight.ExtraBold, 30.sp) // Numbers
  val Figures2 = Format(FontWeight.Medium, 15.sp) // Description
  val SearchBox1 = Format(FontWeight.Normal, 16.sp)
  val SearchBox2 = Format(FontWeight.Normal, 14.sp)
  val Button1 = Format(FontWeight.Bold, 16.sp)
  val Button2 = Format(FontWeight.Normal, 16.sp)
  val InputField = Format(FontWeight.Normal, 14.sp)
}