package com.example.guest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guest.data.Guest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestListScreen() {
    var guestToEdit by remember { mutableStateOf<Guest?>(null) }

    var guestList = remember {
        mutableStateListOf(
            Guest(
                id = 1,
                name = "Fulano da Silva",
                email = "fulano@upf.br",
                phone = "54 99332222"
            ),
            Guest(
                id = 2,
                name = "Beltrano da Silva",
                email = "beltrano@upf.br",
                phone = "54 99332227"
            ),
            Guest(
                id = 3,
                name = "Ciclano da Silva",
                email = "beltrano@upf.br",
                phone = "54 99332225"
            ),
            Guest(
                id = 4,
                name = "João da Silva",
                email = "joao@upf.br",
                phone = "54 343434"
            ),
        )
    }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Convidados") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "Informações")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(guestList.size) { index ->
                GuestCard(guestList[index],
                            onDelete = {
                                guestList.removeAt(index)
                            },
                            onEdit = {
                                showDialog = true
                                guestToEdit = guestList[index]
                            }
                )
            }
        }
    }
    GuestDialogForm(
        isOpen = showDialog,
        onDismiss = {
            showDialog = false
        },
        onSave = { guest ->
            if (guestToEdit == null) {
                guestList.add(guest)
            } else {
                guestList[guestList.indexOf(guestToEdit)] = guest
            }
            showDialog = false
        },
        guest = guestToEdit
    )
}

@Composable
fun GuestCard(guest: Guest,
              onDelete: (Guest) -> Unit,
              onEdit: (Guest) -> Unit)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(guest.name, style = MaterialTheme.typography.titleMedium)
                Text(guest.email, style = MaterialTheme.typography.bodyMedium)
                Text(guest.phone, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = {
                    onEdit(guest)
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Alterar")
                }
                IconButton(onClick = {
                    onDelete(guest)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                }
            }
        }
    }
}