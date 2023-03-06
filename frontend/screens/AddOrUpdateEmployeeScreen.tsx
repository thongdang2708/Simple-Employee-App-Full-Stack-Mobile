
import React, { useContext, useEffect, useLayoutEffect } from 'react';
import {View, Text, StyleSheet} from "react-native";
import { dispatchStore, RootState } from '../store/redux/store';
import { getAllDepartments } from '../store/redux/actions/DepartmentActions';
import { Picker } from '@react-native-community/picker';
import AuthContext from '../store/context/AuthContext';
import { useSelector } from 'react-redux';
import { useState } from 'react';
import { Department } from '../store/redux/reducers/DepartmentReducers';
import colors from '../constants/colors';
import { SelectList } from 'react-native-dropdown-select-list';
import InputForUpdating from '../components/InputForUpdating';
import ButtonForMainScreen from '../ui/ButtonForMainScreen';
import { EmployeeProps } from '../store/redux/reducers/EmployeeReducers';
import { addEmployee } from '../store/redux/actions/EmployeeActions';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootParamList } from '../App';
import { updateEmployee } from '../store/redux/actions/EmployeeActions';
import { EmployeeInputProps } from '../store/redux/actions/EmployeeActions';
import {Ionicons} from "@expo/vector-icons";
import { deleteEmployee } from '../store/redux/actions/EmployeeActions';


function AddOrUpdateEmployeeScreen({navigation, route} : any) {

  //Get route params
  let id = route.params?.id;
  let addOrUpdate = !!id;

  //Get auth context
  let {token} = useContext(AuthContext);

  //Get deparment from redux
  let {departments} = useSelector((state : RootState) => state.department);

  //Set navigation
  let navigation2 = useNavigation<StackNavigationProp<RootParamList>>();

  //Get manager id from user in redux
  let {managerId} = useSelector((state : RootState) => state.user);

  //Get employees from redux
  let {employees, isError, message} = useSelector((state : RootState) => state.employee);

  //Filter single employee from the total array
  let singleEmployee = employees.filter((employee : EmployeeProps) => employee.id === id)[0];
  
  //Set effect to set title

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: !addOrUpdate ? "Add Employee Screen" : "Update Employee Screen"
    })
  }, [navigation]);

  //Set effect to get all departments
  useEffect(() => {
    dispatchStore(getAllDepartments(token as string) as any);
  },[]);


  let castedDepartments = departments as Department[];
  console.log(castedDepartments);
  //Cast to selection box
  let selectionBox = castedDepartments.map((department : Department) => ({
      key: department.id,
      value: department.name
  }));

  console.log(selectionBox);

  //Set state for name input
  
  let [name, setName] = useState({
    name: !addOrUpdate ? "" : singleEmployee?.name
  });

  //Set state for validating input name
  let [isNameValid, setIsNameValid] = useState(true);

  //Set state for selection box

  let [selectedDepartment, setSelectedDepartment] = useState({
      key: !addOrUpdate ? "" : singleEmployee?.department_id,
      value: !addOrUpdate ? "" : singleEmployee?.department_name
  }); 

  //Set selected department is valid
  let [isDepartmentValid, setIsDepartmentValid] = useState(true);

  //Handle change for inputs in selection box
  const handleChange = (key : string, valueName : number) => {

    let object = selectionBox.find((value : any) => {
      return value.key === valueName
  });
    
      setSelectedDepartment((values) => {
        return {
          ...values,
          [key]: valueName,
          value: object?.value
        } 
      });
      
  }


  //Handle change for name input
  const handleChangeName = (key: string, value : string) => {

    setName((values) => {
      return {
        ...values,
        [key]: value
      }
    })
  } 
 
  //Handle submit
  const handleSubmit = () => {
    console.log(selectedDepartment);
    let nameIsValid = name.name.length >= 2;
    let departmentIsValid = selectedDepartment?.value?.length > 0;

    if (!nameIsValid || !departmentIsValid) {
      setIsNameValid(nameIsValid);
      setIsDepartmentValid(departmentIsValid);
      setTimeout(() => {
        setIsNameValid(true);
        setIsDepartmentValid(true);
      }, 3000);
      return;
    }
    
    if (!addOrUpdate) {
      dispatchStore(addEmployee(token as string, selectedDepartment.key, managerId, {
        name: name.name
      }) as any);
    } else {
      dispatchStore(updateEmployee(token as string, id, selectedDepartment.key as number, {
        name: name.name
      }) as any);
    }

    navigation2.navigate("EmployeeScreen");
   
  }

    //Handle delete
    const handleDelete = () => {
      dispatchStore(deleteEmployee(token as string, id) as any);
      navigation2.navigate("EmployeeScreen");
    };

  //Set page when there are no created departments

  if (castedDepartments?.length == 0) {
    return (
      <View style={styles.screen}>
        <Text style={styles.errorText}> There are no departments. Cannot add employees! </Text>
      </View>
    )
  }

  return (
    <View style={styles.screen}>
        <Text style={styles.title}> {!addOrUpdate ? "Add Employee" : "Update Employee"} </Text>

   
        <InputForUpdating label='Name of employee:' textInputConfig={{
            placeholder: "Enter your name",
            autoCapitalize: 'none', 
            autoCorrect: false,
            value: name.name,
            onChangeText: (text : string) => handleChangeName("name", text)
        }}
        inputValid={isNameValid == false}
        textValid={"Name must have at least 2 characters!"}
        />
        
        
       <View style={styles.selectionBoxContainer}>
        <Text style={styles.label}> Department: </Text>
        <SelectList defaultOption={!addOrUpdate ? {key: "", value: ""} : selectedDepartment} data={selectionBox} setSelected={(key : number) => handleChange("key", key)} dropdownStyles={{backgroundColor: colors.offWhite}} dropdownTextStyles={{fontWeight: "bold"}} boxStyles={{borderWidth: 2, backgroundColor: colors.grey, borderColor: "black"}}/>
        </View>
        {!isDepartmentValid && ( <Text style={styles.warningText}> Please add a department of employee! </Text>)}

        <ButtonForMainScreen onPress={handleSubmit}>
            {!addOrUpdate ? "Add Employee" : "Update Employee"}
        </ButtonForMainScreen>

        {addOrUpdate && (
          <ButtonForMainScreen onPress={handleDelete}>
            <Ionicons name="trash-outline" color={colors.orange} size={30}/>
          </ButtonForMainScreen>
        )}
    </View>
  )
}

const styles = StyleSheet.create({
    screen: {
      flex: 1,
      padding: 10,
      backgroundColor: colors.skinColor
    },
    errorText: {
      fontSize: 18,
      textAlign: "center",
      marginVertical: 15,
      color: colors.red
    },
    title: {
      textAlign: "center",
      fontSize: 24,
      fontWeight: "bold",
      marginVertical: 18
    },
    selectionBoxContainer: {
      marginHorizontal: 15,
      marginVertical: 20
    },
    label: {
      fontWeight: "bold",
      fontSize: 18,
      marginVertical: 10
    },
    warningText: {
      fontSize: 16,
      color: colors.red,
      fontWeight: "bold",
      marginHorizontal: 14,
      marginVertical: 16
    }
}); 



export default AddOrUpdateEmployeeScreen;