

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewsPage(
	onBack: () -> Unit = {},
	reviews: List<Review> = emptyList(),
	modifier: Modifier = Modifier,
) {
	var searchQuery by remember { mutableStateOf("") }
	val filteredReviews = reviews.filter { review ->
		searchQuery.isBlank() || review.reviewer.getFullName().contains(searchQuery, ignoreCase = true)
	}

	Column(modifier = modifier.fillMaxSize().background(Colours.LightModeBackground1)) {
		ReviewsHeader(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it }, onBack = onBack)
		LazyColumn(
			modifier = Modifier.weight(1f),
			contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 17.dp, bottom = 18.dp),
			verticalArrangement = Arrangement.spacedBy(14.dp),
		) {
			item { RatingSummary(reviews) }
			items(filteredReviews) { review -> IndividualReview(review) }
		}
	}
}


// components used only in reviews page

@Composable
private fun ReviewsHeader(searchQuery: String, onSearchQueryChange: (String) -> Unit, onBack: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth().background(Colours.DarkModeBackground)
			.padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 15.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(15.dp)) {
			BackButton(onClick = onBack)
			Column {
				Text("Reviews", color = Colours.DarkModeText, fontSize = TextFormatting.MenuBarTitle.size, fontWeight = TextFormatting.MenuBarTitle.weight)
				Text("See what people thought about your rides", color = Colours.DarkModeText, fontSize = TextFormatting.Text1.size, fontWeight = TextFormatting.Text1.weight)
			}
		}
		OutlinedTextField(
			value = searchQuery,
			onValueChange = onSearchQueryChange,
			modifier = Modifier.fillMaxWidth().height(40.dp),
			placeholder = { Text("Search for a person", color = Colours.DarkModeText) },
			leadingIcon = { SearchIcon() },
			shape = RoundedCornerShape(15.dp),
			 singleLine = true,
			colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
				focusedTextColor = Colours.LightModeText,
				unfocusedTextColor = Colours.DarkModeText,
				focusedBorderColor = Colours.Accent,
				unfocusedBorderColor = Colours.DarkModeBorder,
				focusedBackgroundColor = Colours.LightModeBackground
				unfocusedBackgroundColor = Colours.DarkModeBackground
				cursorColor = Colours.DarkModeText,
			),
		)
	}
}

@Composable
private fun RatingSummary(reviews: List<Review>) {
	val rating = if (reviews.isEmpty()) 0f else reviews.map { it.rating }.average().toFloat()
	Row(
		modifier = Modifier.fillMaxWidth().padding(horizontal = 44.dp, vertical = 2.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(String.format("%.1f", rating), color = Colours.LightModeText, fontSize = TextFormatting.Heading1.size, fontWeight = TextFormatting.Heading1.weight)
			Text("(${reviews.size} reviews)", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight)
		}
		StarGlyph(filled = true, modifier = Modifier.size(80.dp))
		Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
			for (stars in 5 downTo 1) {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text("${stars}", color = Colours.LightModeText, fontSize = TextFormatting.Text3.size, fontWeight = TextFormatting.Text3.weight, modifier = Modifier.width(12.dp))
					Spacer(Modifier.width(7.dp))
					
					// progress bar component
					Box(Modifier.weight(1f).height(2.dp).background(if (stars <= rating.toInt()) Colours.LightModePrimary else Colours.LightModeSecondary))
				}
			}
		}
	}
}


// components primarily used in the reviews page

@Composable
fun IndividualReview(review: Review) {
	reviewer = review.reviewer
	Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).clip(RoundedCornerShape(20.dp)).border(1.dp, Colours.LightModeBorder, RoundedCornerShape(20.dp)).background(Colours.LightModeBackground2).padding(horizontal = 15.dp,vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = content
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            reviewer.getProfilePic()
            Spacer(Modifier.width(5.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${reviewer.getFormattedFirstName()}", color = Colours.LightModeText, fontSize = TextFormatting.Boxes1.size, fontWeight = TextFormatting.Boxes1.weight)
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