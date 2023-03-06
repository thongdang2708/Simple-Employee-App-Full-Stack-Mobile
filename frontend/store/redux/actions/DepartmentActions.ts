
import axios from "axios";
import { Dispatch } from "redux";
import { WEB_URL } from "@env";
import { Department } from "../reducers/DepartmentReducers";
import { DepartmentAction } from "../reducers/DepartmentReducers";

//Fetch all departments

export const getAllDepartments = (token : string) => async (dispatch: Dispatch<DepartmentAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.get(WEB_URL + "/departments/all", config);

    let data = response.data;

    let arrayData = data.map((minorData : any) => (
        {
            id: minorData.id,
            name: minorData.name,
            createdAt: minorData.createdAt,
            manager_id: minorData.manager.id
        }
    ));
  
    dispatch({
        type: "GET_ALL_DEPARTMENTS_SUCCESS",
        payload: arrayData
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "GET_ALL_DEPARTMENTS_FAIL",
            payload: message
        })
    }
} 

//Get single department

export const getSingleDepartment = (token : string, id: number) => async (dispatch : Dispatch<DepartmentAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.get(WEB_URL + "/departments/" + id, config);

    let data = response.data;

    let newObject : Department = {
        id: data.id,
        name: data.name,
        createdAt: data.createdAt,
        manager_id: data.manager.id
    }

    dispatch({
        type: "GET_SINGLE_DEPARTMENT_SUCCESS",
        payload: newObject
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "GET_SINGLE_DEPARTMENT_FAIL",
            payload: message 
        });
    }
}

interface DeparmentInputProps {
    name: string,
    createdAt: string,
}

//Add Department

export const addDepartment = (token : string, input: DeparmentInputProps, managerId : number) => async (dispatch : Dispatch<DepartmentAction>, getState : any) => {
    console.log(input, managerId);
    try {
    
    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };


    let response = await axios.post(WEB_URL + "/departments/manager/" + managerId, input, config);
    console.log(response);
    let data = response.data;
    console.log(data);

    let newObject : Department = {
        id: data.id,
        name: data.name,
        createdAt: data.createdAt,
        manager_id: data.manager.id
    }

    console.log(newObject);

    dispatch({
        type: "ADD_DEPARTMENT_SUCCESS",
        payload: newObject
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "ADD_DEPARTMENT_FAIL",
            payload: message 
        });
    }
};

//Delete department 

export const deleteDepartment = (token : string, id : number) => async (dispatch : Dispatch<DepartmentAction>, getState : any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    await axios.delete(WEB_URL + "/departments/" + id, config);

    dispatch({
        type: "DELETE_DEPARTMENT_SUCCESS",
        payload: id
    });


    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "DELETE_DEPARTMENT_FAIL",
            payload: message 
        });
    }
}

//Update department
export const updateDepartment = (token : string, id : number, input : DeparmentInputProps) => async (dispatch: Dispatch<DepartmentAction>, getState: any) => {

    try {

    let config = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    };

    let response = await axios.put(WEB_URL + "/departments/" + id, input, config);
    
    let data = response.data;

    let newObject : Department = {
        id: data.id,
        name: data.name,
        createdAt: data.createdAt,
        manager_id: data.manager.id
    };

    dispatch({
        type: "UPDATE_DEPARTMENT_SUCCESS",
        payload: newObject
    })

    } catch (error : any) {
        let message = error.response.data.messages[0].toString();

        dispatch({
            type: "UPDATE_DEPARTMENT_FAIL",
            payload: message 
        });
    }
}


