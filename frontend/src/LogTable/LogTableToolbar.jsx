import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import AddIcon from "@mui/icons-material/Add.js";
import EditIcon from "@mui/icons-material/Edit.js";
import DeleteIcon from "@mui/icons-material/Delete.js";
import PropTypes from "prop-types";
import * as React from "react";

import { useEffect } from "react";
import { useSelectedRows, useRows } from "./LogTable";
import axios from "axios";

export default function LogTableToolbar(props) {
    const {rows, setRows} = useRows();
    const { selectedRows, selectRow, selectMultipleRows} = useSelectedRows();
    const { numSelected } = props;

    useEffect(() => {
        console.log("Количество rows:", rows.length);
    }, [rows]); 


    function handleDelete() {
        const timeInMicroseconds = performance.now();
        console.log(`Нажата кнопка Delete, время: ${timeInMicroseconds.toFixed(0)} микросекунд. Selected rows to delete:`, JSON.stringify(selectedRows));
    
        const deleteRequests = selectedRows.map(id =>
            axios.delete(`http://127.0.0.1:8070/api/logentries/${id}`).then(() => id)
        );
    
        Promise.allSettled(deleteRequests)
            .then(results => {
                console.log("Все запросы на удаление завершились корректно:", results);
    
                const successfullyDeleted = results
                    .filter(result => result.status === "fulfilled")
                    .map(result => result.value);
    
                if (successfullyDeleted.length > 0) {
                    setRows(prevRows => prevRows.filter(row => !successfullyDeleted.includes(row.id)));
                    selectMultipleRows([]);
                }
    
                results.forEach((result, index) => {
                    if (result.status === "fulfilled") {
                        console.log(`ID ${result.value} успешно удалён.`);
                    } else {
                        console.error(`Ошибка удаления ID ${selectedRows[index]}:`, result.reason);
                    }
                });
            })
            .catch(error => {
                console.error('Неизвестная ошибка:', error);
            });
    }
    


    function handleEdit() {
        const timeInMicroseconds = performance.now();
        console.log(`Нажата кнопка Edit, время: ${timeInMicroseconds.toFixed(0)} микросекунд`);
    }

    function handleAdd() {
        const timeInMicroseconds = performance.now();
        console.log(`Нажата кнопка Add, время: ${timeInMicroseconds.toFixed(0)} микросекунд`);
    };

    return (
        <Toolbar
            sx={[
                {
                    pl: { sm: 2 },
                    pr: { xs: 1, sm: 1 },
                }
            ]}
        >
            {numSelected > 0 ? (
                <Typography
                    sx={{ flex: '1 1 100%' }}
                    color="inherit"
                    variant="subtitle1"
                    component="div"
                >
                    Выбрано записей: {numSelected}
                </Typography>
            ) : (
                <Typography
                    sx={{ flex: '1 1 100%' }}
                    variant="h6"
                    id="tableTitle"
                    component="div"
                >
                    История
                </Typography>
            )}
            {numSelected === 0 ? (
                <Tooltip title="Add">
                    <IconButton onClick={handleAdd}>
                        <AddIcon />
                    </IconButton>
                </Tooltip>
            ) : numSelected === 1 ? (
                <>
                    <Tooltip title="Edit">
                        <IconButton>
                            <EditIcon onClick={handleEdit}/>
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete">
                        <IconButton onClick={handleDelete}>
                            <DeleteIcon />
                        </IconButton>
                    </Tooltip>
                </>
            ) : numSelected && rows.length !== 0 > 1 ? (
                <Tooltip title="Delete">
                    <IconButton onClick={handleDelete}>
                        <DeleteIcon />
                    </IconButton>
                </Tooltip>
            ) : null}

        </Toolbar>
    );
}

LogTableToolbar.propTypes = {
    numSelected: PropTypes.number.isRequired,
};

