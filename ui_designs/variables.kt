object Colours {
  val Accent: Color = Color(0xFF1A9E52)

  // Light mode colors
  object LightMode {
    val Background1: Color = Color(0xFFF5FFF8)
    val Background2: Color = Color(0xFFFFFFFF)
    val Primary: Color = Color(0xFF0A5C2E)
    val Secondary: Color = Color(0xFFB5DDC3)
    val Border: Color = Color(0xFFB5DDC3)
    val Text: Color = Color(0xFF0A5C2E)
    val InputText: Color = Color(0xFF1A9E52)
  }

  // Dark mode colors
  object DarkMode {
    val Background1: Color = Color(0xFF0A5C2E)
    val Background2: Color = Color(0xFF2A7149)
    val Primary: Color = Color(0xFFF5FFF8)
    val Secondary: Color = Color(0xFFB5DDC3)
    val Border: Color = Color(0xFFF5FFF8)
    val Text: Color = Color(0xFFFFFFFF)
    val InputText: Color = Color(0xFFFFFFFF)
  }
    
  // Misc colours
  val RedBackground: Color = Color(0xFFE49E99)

    
  // Button colours
  object Buttons {
    object Unselected {
      background: Color = Colours.LightMode.Background2,
      border: Color = Colours.LightMode.Border,
      text: Color = Colours.Accent,
      icon: Color = Colours.Accent,
    }
    object Selected {
      background: Color = Colours.LightMode.Secondary,
      border: Color = Colours.Accent,
      text: Color = Colours.Text,
      icon: Color = Colours.Primary,
    }
  }
}
data class ColourScheme(
  val background: Color,
  val border: Color,
  val text: Color,
  val icon: Color,
  val primary: Color,
  val secondary: Color
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