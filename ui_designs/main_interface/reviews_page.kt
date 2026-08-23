

// components primarily used in the reviews page

@Composable
fun IndividualReview(review: Review) {
	Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.LightModeBorder, RoundedCornerShape(20.dp)).background(Colours.LightModeBackground2).padding(horizontal = 15.dp,vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = content
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ProfilePic(initials = review.reviewer.getInitials())
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${review.reviewer.getFormattedFirstName()}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
                Text("${review.startCity} → ${review.endCity}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes2.size, fontWeight = TextFormatting.Boxes2.weight)
            }
            StarRating(filled = review.rating)
        }
        Text("“${review.quote}”", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
    }
}

@Composable
fun StarRating(filled: Int = 5, theme: Theme = Theme.Light, modifier: Modifier = Modifier) {
    lightMode = theme == Theme.Light

	Row(
		modifier = modifier
			.background(Color.Transparent, RoundedCornerShape(20.dp))
			.padding(horizontal = if (lightMode) 0.dp else 5.dp, vertical = if (lightMode) 0.dp else 2.dp),
		horizontalArrangement = Arrangement.spacedBy(1.dp),
	) {
		repeat(5) { index ->
			StarGlyph(filled = index < filled, color = if (lightMode) Colours.LightModePrimary else Colours.DarkModeSecondary, modifier = Modifier.size(20.dp))
		}
	}
}
@Composable
private fun StarGlyph(filled: Boolean, color: Color, modifier: Modifier = Modifier) {
	Canvas(modifier) {
		val center = Offset(size.width / 2f, size.height / 2f)
		val outer = size.minDimension * 0.44f
		val inner = outer * 0.42f
		val star = Path()
		repeat(10) { index ->
			val radius = if (index % 2 == 0) outer else inner
			val angle = Math.toRadians(-90.0 + index * 36.0)
			val point = Offset(center.x + (kotlin.math.cos(angle) * radius).toFloat(), center.y + (kotlin.math.sin(angle) * radius).toFloat())
			if (index == 0) star.moveTo(point.x, point.y) else star.lineTo(point.x, point.y)
		}
		star.close()
		drawPath(star, color, style = if (filled) androidx.compose.ui.graphics.drawscope.Fill else Stroke(width = 1.5f, join = StrokeJoin.Round))
	}
}