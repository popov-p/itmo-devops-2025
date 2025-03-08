import React, { useState } from 'react';

import {createContext, useContext } from "react";

const RowsContext = createContext();
export const useRows = () => useContext(RowsContext);

export const RowsProvider = ({ children }) => {
    const [rows, setRows] = useState([]);

    const updateRows = (newRows) => {
        setRows(newRows);
    };

    return (
        <RowsContext.Provider value={{ rows, setRows: updateRows }}>
            {children}
        </RowsContext.Provider>
    );
};


const SelectedRowsContext = createContext();

export const useSelectedRows = () => useContext(SelectedRowsContext);

export const SelectedRowsProvider = ({ children }) => {
    const [selectedRows, setSelectedRows] = useState([]);

    const selectRow = (event, id) => {
        const selectedIndex = selectedRows.indexOf(id);
        let newSelected = [];

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selectedRows, id);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selectedRows.slice(1));
        } else if (selectedIndex === selectedRows.length - 1) {
            newSelected = newSelected.concat(selectedRows.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selectedRows.slice(0, selectedIndex),
                selectedRows.slice(selectedIndex + 1),
            );
        }

        setSelectedRows(newSelected);
    };


    const selectMultipleRows = (ids) => {
        if (ids.length === 0) {
            setSelectedRows([]);
            return;
        }
        let newSelected = [...selectedRows];

        ids.forEach(id => {
            if (!newSelected.includes(id)) {
                newSelected.push(id);
            }
        });

        setSelectedRows(newSelected);
    };

    return (
        <SelectedRowsContext.Provider value={{ selectedRows, selectRow, selectMultipleRows }}>
            {children}
        </SelectedRowsContext.Provider>
    );
};