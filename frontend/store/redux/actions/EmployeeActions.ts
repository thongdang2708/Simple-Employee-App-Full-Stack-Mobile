
import axios from "axios";
import { Dispatch } from "redux";
import { EmployeeAction } from "../reducers/EmployeeReducers";
import { WEB_URL } from "@env";
import { EmployeeProps } from "../reducers/EmployeeReducers";



//Get a list of employees with pagination

export const getEmployeesWithPagination = (token : string, offset : number, pageSize: number) => async (dispatch : Dispatch<EmployeeAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }; 

    let response = await axios.get(WEB_URL + "/employees/pagination/all/" + offset + "/" + pageSize, config);

    let data = response.data;


    let mappedData : EmployeeProps= data.map((minorData : any) => ({
        id: minorData.id,
        name: minorData.name,
        department_id: minorData.department.id,
        department_name: minorData.department.name,
        manager_id: minorData.manager.id
    }));

    dispatch({
        type: "GET_ALL_EMPLOYEES_PAGINATION_SUCCESS",
        payload: mappedData
    });
    

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "GET_ALL_EMPLOYEES_PAGINATION_FAIL",
            payload: message
        })
    }

} 

//Get all employees

export const getAllEmployees = (token : string) => async (dispatch: Dispatch<EmployeeAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.get(WEB_URL + "/employees/all", config);

    let data = response.data;

    let mappedData : EmployeeProps= data.map((minorData : any) => ({
        id: minorData.id,
        name: minorData.name,
        department_id: minorData.department.id,
        department_name: minorData.department.name,
        manager_id: minorData.manager.id
    }));

    dispatch({
        type: "GET_ALL_EMPLOYEES_SUCCESS",
        payload: mappedData
    });


    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "GET_ALL_EMPLOYEES_FAIL",
            payload: message
        })
    }
}

export interface EmployeeInputProps {
    name: string
}
//Function to add employee

export const addEmployee = (token: string, departmentId : number, managerId: number, input : EmployeeInputProps) => async (dispatch : Dispatch<EmployeeAction>, getState : any) => {
    try {
    
    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.post(WEB_URL + "/employees/department/" + departmentId + "/manager/" + managerId, input, config);
   
    let data = response.data;

    let tempArray = [];
    tempArray.push(data);

    let mappedData : EmployeeProps = tempArray.map((minorData : any) => ({
        id: minorData.id,
        name: minorData.name,
        department_id: minorData.department.id,
        department_name: minorData.department.name,
        manager_id: minorData.manager.id
    }))[0];

    
    dispatch({
        type: "ADD_EMPLOYEE_SUCCESS",
        payload: mappedData
    })
    
    
    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "ADD_EMPLOYEE_FAIL",
            payload: message
        })
    }
}


//Function to update employee

export const updateEmployee = (token : string, employeeId : number, departmentId : number, input: EmployeeInputProps) => async (dispatch : Dispatch<EmployeeAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.put(WEB_URL + "/employees/" + employeeId + "/department/" + departmentId, input, config);

    let data = response.data;

    let tempArray = [];
    tempArray.push(data);

    let mappedData : EmployeeProps = tempArray.map((minorData : any) => ({
        id: minorData.id,
        name: minorData.name,
        department_id: minorData.department.id,
        department_name: minorData.department.name,
        manager_id: minorData.manager.id
    }))[0];

    dispatch({
        type: "UPDATE_EMPLOYEE_SUCCESS",
        payload: mappedData
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "UPDATE_EMPLOYEE_FAIL",
            payload: message
        })

    }
};

//Delete employee

export const deleteEmployee = (token : string, employeeId: number) => async (dispatch : Dispatch<EmployeeAction>, getState : any) => {

    try {
    
    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    await axios.delete(WEB_URL + "/employees/" + employeeId, config);

    dispatch({
        type: "DELETE_EMPLOYEE_SUCCESS",
        payload: employeeId
    })

    } catch (error : any) {
        

        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "DELETE_EMPLOYEE_FAIL",
            payload: message
        })
    }
};