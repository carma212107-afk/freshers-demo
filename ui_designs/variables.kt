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
  val DarkModeBackground: Color = Color(0xFF0A5C2E)
  val DarkModePrimary: Color = Color(0xFF2A7149)
  val DarkModeSecondary: Color = Color(0xFFF5FFF8)
  val DarkModeBorder: Color = Color(0xFFF5FFF8)
  val DarkModeText: Color = Color(0xFFFFFFFF)
  val DarkModeInputText: Color = Color(0xFFFFFFFF)
}


object Dimensions {
  val currentUIX = 402.dp
  val currentUIY = 874.dp
}


object TextFormatting {
  val MenuBarTitle(weight = FontWeight.Black, size = 30.sp)
  val IntroTitle(weight = FontWeight.Black, size = 50.sp)
  val Heading1(weight = FontWeight.Black, size = 35.sp) // Title
  val Heading2(weight = FontWeight.ExtraBold, size = 25.sp) // Section title
  val Text1(weight = FontWeight.Normal, size = 16.sp) // Title description
  val Text2(weight = FontWeight.Bold, size = 20.sp) // Key info
  val Text3(weight = FontWeight.Normal, size = 15.sp) // Text description
  val SmallText1(weight = FontWeight.Bold, size = 12.sp) // Key info
  val SmallText2(weight = FontWeight.Normal, size = 12.sp) // Text description
  val Boxes1(weight = FontWeight.Bold, size = 15.sp) // Key info
  val Boxes2(weight = FontWeight.Light, size = 14.sp) // Description
  val Figures1(weight = FontWeight.ExtraBold, size = 30.sp) // Numbers
  val Figures2(weight = FontWeight.Medium, size = 15.sp) // Description
  val SearchBox1(weight = FontWeight.Normal, size = 16.sp)
  val SearchBox2(weight = FontWeight.Normal, size = 14.sp)
  val Button1(weight = FontWeight.Bold, size = 16.sp)
  val Button2(weight = FontWeight.Normal, size = 16.sp)
  val InputField(weight = FontWeight.Normal, size = 14.sp)
}