package com.example.cocinacapital.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object AuthColors {
    val Blue = Color(0xFF1460B5)
    val Sky = Color(0xFFE3F3FD)
    val Background = Color(0xFFFBFDFF)
    val Ink = Color(0xFF18344B)
    val Secondary = Color(0xFF5F7383)
    val Border = Color(0xFFCEDDE8)
    val Placeholder = Color(0xFF84939E)
    val Disabled = Color(0xFFE1E9EF)
    val DisabledText = Color(0xFF8C9CA8)
    val Error = Color(0xFFB3261E)
}

@Composable
internal fun AuthLayout(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.Background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        AuthHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 28.dp, vertical = 32.dp)
        ) {
            Text(
                text = title,
                color = AuthColors.Ink,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = subtitle,
                color = AuthColors.Secondary,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(30.dp))

            content()
        }
    }
}

@Composable
private fun AuthHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AuthColors.Sky)
    ) {
        Canvas(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(110.dp)
        ) {
            val lineColor = Color(0xFFACD9F7)

            drawCircle(
                color = lineColor,
                radius = size.width * 0.48f,
                center = Offset(size.width * 0.85f, size.height * 0.78f),
                style = Stroke(width = 1.dp.toPx())
            )

            drawCircle(
                color = lineColor,
                radius = size.width * 0.36f,
                center = Offset(size.width * 0.85f, size.height * 0.78f),
                style = Stroke(width = 1.dp.toPx())
            )

            val leaf = Path().apply {
                moveTo(size.width * 0.40f, size.height * 0.82f)
                cubicTo(
                    size.width * 0.40f, size.height * 0.54f,
                    size.width * 0.61f, size.height * 0.43f,
                    size.width * 0.77f, size.height * 0.44f
                )
                cubicTo(
                    size.width * 0.76f, size.height * 0.66f,
                    size.width * 0.61f, size.height * 0.79f,
                    size.width * 0.40f, size.height * 0.82f
                )
                close()
            }

            drawPath(
                path = leaf,
                color = Color(0xFF6FAEE1),
                style = Stroke(width = 1.dp.toPx())
            )

            drawLine(
                color = Color(0xFF6FAEE1),
                start = Offset(size.width * 0.38f, size.height * 0.86f),
                end = Offset(size.width * 0.67f, size.height * 0.55f),
                strokeWidth = 1.dp.toPx()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .align(Alignment.Center)
                .padding(horizontal = 28.dp, vertical = 28.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(AuthColors.Blue, RoundedCornerShape(17.dp)),
                contentAlignment = Alignment.Center
            ) {
                ChefMark(Modifier.size(34.dp))
            }

            Column {
                Text(
                    text = "Cocina\nCapital",
                    color = AuthColors.Blue,
                    fontSize = 25.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Los mejores platillos a tu alcance.",
                    color = AuthColors.Secondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ChefMark(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height

        val hat = Path().apply {
            moveTo(w * 0.27f, h * 0.57f)
            cubicTo(
                w * 0.07f, h * 0.54f,
                w * 0.08f, h * 0.27f,
                w * 0.30f, h * 0.27f
            )
            cubicTo(
                w * 0.34f, h * 0.07f,
                w * 0.66f, h * 0.07f,
                w * 0.70f, h * 0.27f
            )
            cubicTo(
                w * 0.92f, h * 0.27f,
                w * 0.93f, h * 0.54f,
                w * 0.73f, h * 0.57f
            )
            lineTo(w * 0.73f, h * 0.82f)
            lineTo(w * 0.27f, h * 0.82f)
            close()
        }

        drawPath(
            path = hat,
            color = Color.White,
            style = Stroke(width = 1.3.dp.toPx())
        )

        drawLine(
            color = Color.White,
            start = Offset(w * 0.28f, h * 0.66f),
            end = Offset(w * 0.72f, h * 0.66f),
            strokeWidth = 1.3.dp.toPx()
        )
    }
}

@Composable
internal fun AuthField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            color = AuthColors.Ink,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(13.dp),
            textStyle = TextStyle(
                fontSize = 15.sp,
                color = AuthColors.Ink
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = AuthColors.Placeholder,
                    fontSize = 15.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = if (password) {
                {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            imageVector = if (showPassword) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
                            contentDescription = if (showPassword) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else {
                null
            },
            visualTransformation = if (password && !showPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (password) {
                    KeyboardType.Password
                } else {
                    keyboardType
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedBorderColor = AuthColors.Blue,
                unfocusedBorderColor = AuthColors.Border,
                errorBorderColor = AuthColors.Error,
                cursorColor = AuthColors.Blue,
                focusedLeadingIconColor = AuthColors.Blue,
                unfocusedLeadingIconColor = AuthColors.Placeholder,
                focusedTrailingIconColor = AuthColors.Blue,
                unfocusedTrailingIconColor = AuthColors.Placeholder
            )
        )

        if (supportingText != null) {
            Spacer(Modifier.height(6.dp))

            Text(
                text = supportingText,
                color = if (isError) {
                    AuthColors.Error
                } else {
                    AuthColors.Secondary
                },
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
internal fun AuthPrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AuthColors.Blue,
            contentColor = Color.White,
            disabledContainerColor = AuthColors.Disabled,
            disabledContentColor = AuthColors.DisabledText
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun AuthFooter(
    question: String,
    action: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question,
            color = AuthColors.Secondary,
            fontSize = 14.sp
        )

        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = AuthColors.Blue
            )
        ) {
            Text(
                text = action,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}