
interface ManagerProps {
    managerForId: number,
    address: string,
    city: string,
    role: string
}

interface StateProps {
    manager: ManagerProps | {},
    isSuccess: boolean,
    isError: boolean,
    message: string
}

let initialValues : StateProps = {
    manager: {},
    isSuccess: false,
    isError: false,
    message: ""
}

export interface ManagerAction {
    type: string,
    payload?: any
}

export const managerReducer = (state : StateProps = initialValues, action : ManagerAction) => {

    switch (action.type) {
        case "FETCH_MANAGER_SUCCESSFULLY":
            return {
                ...state,
                manager: action.payload,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "FETCH_MANAGER_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "UPDATE_MANAGER_INFO_SUCCESS":
            return {
                ...state,
                manager: action.payload,
                isError: false,
                message: ""
            }
        case "UPDATE_MANAGER_INFO_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        default:
            return state
    }

}