package com.whatsapp.task.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AuthorizationBackground = Color(0xFFF7F7F7)
private val PrimaryText = Color(0xFF111111)
private val SecondaryText = Color(0xFF777777)
private val WhatsAppBlue = Color(0xFF007AFF)
private val KeyboardBackground = Color(0xFFD1D5DB)
private val KeyBackground = Color(0xFFF8F8F8)

@Composable
fun AuthorizationScreen(
    onDone: () -> Unit
) {

    var phoneNumber by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthorizationBackground)
    ) {

        // --------------------------------------------------
        // TOP BAR
        // --------------------------------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Phone number",
                color = PrimaryText,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Done",
                color = if (phoneNumber.isNotEmpty()) {
                    WhatsAppBlue
                } else {
                    Color.LightGray
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(
                    enabled = phoneNumber.isNotEmpty()
                ) {
                    onDone()
                }
            )
        }

        // --------------------------------------------------
        // INSTRUCTION
        // --------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 30.dp,
                    vertical = 18.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Please confirm your country code and",
                color = PrimaryText,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "enter your phone number",
                color = PrimaryText,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier.height(1.dp)
        )

        // --------------------------------------------------
        // COUNTRY
        // --------------------------------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 14.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "United States",
                color = WhatsAppBlue,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                color = Color.Gray,
                fontSize = 28.sp
            )
        }

        Spacer(
            modifier = Modifier.height(1.dp)
        )

        // --------------------------------------------------
        // PHONE INPUT
        // --------------------------------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "+1",
                color = PrimaryText,
                fontSize = 18.sp,
                modifier = Modifier.width(50.dp)
            )

            Text(
                text = if (phoneNumber.isEmpty()) {
                    "phone number"
                } else {
                    phoneNumber
                },
                color = if (phoneNumber.isEmpty()) {
                    Color.LightGray
                } else {
                    PrimaryText
                },
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // --------------------------------------------------
        // NUMBER KEYPAD
        // --------------------------------------------------

        NumberPad(
            onNumberClick = { number ->
                if (phoneNumber.length < 15) {
                    phoneNumber += number
                }
            },
            onBackspaceClick = {
                if (phoneNumber.isNotEmpty()) {
                    phoneNumber = phoneNumber.dropLast(1)
                }
            }
        )
    }
}

@Composable
private fun NumberPad(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(KeyboardBackground)
            .padding(
                horizontal = 8.dp,
                vertical = 6.dp
            ),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        NumberRow(
            numbers = listOf("1", "2", "3"),
            onNumberClick = onNumberClick
        )

        NumberRow(
            numbers = listOf("4", "5", "6"),
            onNumberClick = onNumberClick
        )

        NumberRow(
            numbers = listOf("7", "8", "9"),
            onNumberClick = onNumberClick
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            NumberKey(
                text = "",
                modifier = Modifier.weight(1f),
                enabled = false
            )

            NumberKey(
                text = "0",
                modifier = Modifier.weight(1f),
                onClick = {
                    onNumberClick("0")
                }
            )

            NumberKey(
                text = "⌫",
                modifier = Modifier.weight(1f),
                onClick = onBackspaceClick
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

@Composable
private fun NumberRow(
    numbers: List<String>,
    onNumberClick: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        numbers.forEach { number ->

            NumberKey(
                text = number,
                modifier = Modifier.weight(1f),
                onClick = {
                    onNumberClick(number)
                }
            )
        }
    }
}

@Composable
private fun NumberKey(
    text: String,
    modifier: Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {

    Box(
        modifier = modifier
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (enabled) {
                    KeyBackground
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(
                enabled = enabled && onClick != null
            ) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = PrimaryText,
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )
    }
}