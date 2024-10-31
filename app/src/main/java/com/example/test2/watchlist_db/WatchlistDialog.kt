package com.example.test2.watchlist_db

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun WatchlistInputDialog(
    isDialogOpen: MutableState<Boolean>,
    onSave: (Watchlist) -> Unit,
    watchlist: Watchlist? = null
) {
    //val title = remember { mutableStateOf(watchlist?.title ?: "") }
    val description = remember { mutableStateOf(watchlist?.description ?: "") }
    val amount = remember { mutableStateOf(watchlist?.amount?.toString() ?: "") }
    val date = remember { mutableStateOf(watchlist?.date ?: System.currentTimeMillis()) }
    val coroutineScope = rememberCoroutineScope()
    //33
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val dateString = remember { mutableStateOf(dateFormat.format(Date(date.value))) }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date.value

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            date.value = calendar.timeInMillis
            dateString.value = dateFormat.format(Date(date.value))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AlertDialog(
        onDismissRequest = { isDialogOpen.value = false },
        title = {
            Text(
                text =
                if (watchlist == null)
                    "Add Watchlist"
                else "Edit Watchlist"
            ) },
        text = {
            Column {
                /*OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text("Movie Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))*/
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount.value,
                    onValueChange = { amount.value = it },
                    label = { Text("Movie Cost") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            datePickerDialog.show()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = dateString.value,
                        onValueChange = {},
                        label = { Text("Watching Date") },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val watchlistToSave = Watchlist(
                        //title = title.value,
                        description = description.value,
                        amount = amount.value.toDoubleOrNull() ?: 0.0,
                        date = date.value
                    )
                    coroutineScope.launch {
                        onSave(watchlistToSave)
                        isDialogOpen.value = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = { isDialogOpen.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    )
}