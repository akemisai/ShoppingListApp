package com.example.shoppinglistapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator


data class ShoppingItem(
    var name: String,
    var quantity: String,
    var isChecked: Boolean = false
) { var isCheckedState by mutableStateOf(isChecked)}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShoppingListApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    val shoppingList = remember { mutableStateListOf<ShoppingItem>() }

    val totalItems = shoppingList.size
    val checkedItems = shoppingList.count { it.isCheckedState }
    val progress = if (totalItems > 0) checkedItems.toFloat() / totalItems else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter item name") }
        )
        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Enter item quantity") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton(
            onClick = {
                if (name.isEmpty() || quantity.isEmpty()) {
                    return@ElevatedButton
                }
                shoppingList.add(ShoppingItem(name, quantity))
                name = ""
                quantity = ""
            }
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Checked items: $checkedItems/$totalItems")
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(shoppingList) { shoppingItem ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Checkbox(
                        checked = shoppingItem.isCheckedState,
                        onCheckedChange = { isChecked ->
                            shoppingItem.isCheckedState = isChecked
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${shoppingItem.name}: ${shoppingItem.quantity}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListApp()
}