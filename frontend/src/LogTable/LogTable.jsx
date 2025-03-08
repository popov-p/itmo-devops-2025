import * as React from 'react';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Checkbox from '@mui/material/Checkbox';
import { useEffect, useState } from "react";
import axios from "axios";

import LogTableHead from './LogTableHead.jsx'
import LogTableToolbar from './LogTableToolbar.jsx'

import { useRows, useSelectedRows } from './LogContexts.jsx';

export default function LogTable() {
    const { rows, setRows } = useRows([]);
    const { selectedRows, selectRow, selectMultipleRows } = useSelectedRows();
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        console.log("Количество selectedRows:", selectedRows.length);
    }, [selectedRows]);

    useEffect(() => {
        console.log("Количество  rows:", rows.length);
        const totalPages = Math.ceil(rows.length / rowsPerPage);

        console.log("totalPages: ", totalPages,
                    "rows.length: ", rows.length);

        if (rows.length === 0) {
            setPage(0);
        } else if (page >= totalPages) {
            setPage(Math.max(0, totalPages - 1));
        }

    }, [rows]);

    useEffect(() => {
        axios.get('http://127.0.0.1:8070/api/logentries')
            .then((response) => {
                setRows(response.data.reverse());
                setLoading(false);
                console.log("Данные успешно загружены !", "response data: ", response.data);
            })
            .catch((error) => {
                setRows([]);
                setLoading(false);
                console.log("Ошибка загрузки данных с бэкенда: ", error)
            });
    }, []);


    const handleSelectAllClick = (event) => {
        console.log('---> handle click', 'is checked:', event.target.checked,
            ', rows length: ',
            rows.length, 'selected rows:', selectedRows.length)
        if (event.target.checked && rows.length === selectedRows.length
        ) {
            selectMultipleRows([]);
            return;
        }
        else if (event.target.checked && selectedRows.length === 0) {
            console.log('---> not checked and selecting all')
            const newSelected = visibleRows.map((row) => row.id);
            console.log(newSelected);
            selectMultipleRows(newSelected);
            return;
        }
        else if (event.target.checked && 0 < selectedRows.length < rows.length
        ) {
            console.log('---> intermediate')
            selectMultipleRows([]);
            return;
        }
        else if (!event.target.checked && rows.length === selectedRows.length) {
            console.log('---> not checked and selected all')
            selectMultipleRows([]);
            return;
        }
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
        selectMultipleRows([]);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
        page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

    const visibleRows = React.useMemo(
        () =>
            [...rows]
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage),
        [rows, page, rowsPerPage],
    );

    return (loading ? <></> : (
        <Box sx={{ width: '100%' }}>
            <Paper sx={{ width: '100%', mb: 2 }}>
                <LogTableToolbar
                    numSelected={selectedRows.length}
                    page={page}
                    rowsPerPage={rowsPerPage}
                />
                <TableContainer>
                    <Table
                        sx={{ minWidth: 750 }}
                        aria-labelledby="tableTitle"
                        size={'medium'}
                    >
                        <LogTableHead
                            numSelected={selectedRows.length}
                            onSelectAllClick={handleSelectAllClick}
                            rowCount={rows.length}
                        />

                        <TableBody>
                            {visibleRows.map((row, index) => {
                                const isItemSelected = selectedRows.includes(row.id);
                                const labelId = `enhanced-table-checkbox-${index}`;

                                return (
                                    <TableRow
                                        hover
                                        onClick={(event) => selectRow(event, row.id)}
                                        role="checkbox"
                                        aria-checked={isItemSelected}
                                        tabIndex={-1}
                                        key={row.id}
                                        selected={isItemSelected}
                                        sx={{ cursor: 'pointer' }}
                                    >
                                        <TableCell padding="checkbox">
                                            <Checkbox
                                                color="primary"
                                                checked={isItemSelected}
                                                inputProps={{
                                                    'aria-labelledby': labelId,
                                                }}
                                            />
                                        </TableCell>
                                        <TableCell
                                            component="th"
                                            id={labelId}
                                            scope="row"
                                            padding="none"
                                            align="left"
                                        >
                                            {row.employeeName}
                                        </TableCell>
                                        <TableCell align="center"
                                            sx={{
                                                maxWidth: '180px',
                                                wordWrap: 'break-word',
                                                whiteSpace: 'normal',
                                            }}
                                        >
                                            {row.logMessage}
                                        </TableCell>
                                        <TableCell align="right">{row.timestamp}</TableCell>
                                    </TableRow>
                                );
                            })}
                            {emptyRows > 0 && (
                                <TableRow
                                    style={{
                                        height: (53) * emptyRows,
                                    }}
                                >
                                    <TableCell colSpan={6} />
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>
        </Box>
    ));
}