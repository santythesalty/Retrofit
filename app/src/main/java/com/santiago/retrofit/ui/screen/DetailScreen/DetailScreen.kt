package com.santiago.retrofit.ui.screen.DetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.santiago.retrofit.data.model.Product

@Composable
fun DetailScreen(productId: Int) {
    val viewModel: DetailViewModel = viewModel(factory = DetailViewModelFactory(productId))
    val uiState by viewModel.detail.observeAsState(UiState())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F0FF) // Fondo suave morado claro
    ) {
        if (uiState.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF6B46C1) // Morado principal
                )
            }
        }

        uiState.product?.let { product ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ProductImage(product = product)
                ProductDetails(product = product)
            }
        }
    }
}

@Composable
private fun ProductImage(product: Product) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = product.image,
                imageLoader = ImageLoader.Builder(context).crossfade(true).build()
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ProductDetails(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748) // Color de texto oscuro
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFECC94B), // Amarillo dorado
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = String.format("%.1f", product.rating.rate),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF718096) // Gris medio
                )
            )
            Text(
                text = "(${product.rating.count} reviews)",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF718096) // Gris medio
                )
            )
        }

        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B46C1) // Morado principal
            )
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF718096) // Gris medio
                    )
                )
                Text(
                    text = product.category.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2D3748) // Color de texto oscuro
                    )
                )
            }
        }

        Text(
            text = "Description",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748) // Color de texto oscuro
            )
        )

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF4A5568), // Gris oscuro
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
            ),
            textAlign = TextAlign.Justify
        )
    }
} 