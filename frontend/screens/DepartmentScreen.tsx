
import React from 'react';
import {View, Text, StyleSheet, ListRenderItem, ScrollView} from "react-native";
import { dispatchStore, RootState } from '../store/redux/store';
import { useEffect } from 'react';
import { Department } from '../store/redux/reducers/DepartmentReducers';
import { useSelector } from 'react-redux';
import { getAllDepartments } from '../store/redux/actions/DepartmentActions';
import { useContext } from 'react';
import AuthContext from '../store/context/AuthContext';
import colors from '../constants/colors';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { FlatList } from 'react-native';
import SingleDeparment from '../components/SingleDeparment';
import { RootParamList } from '../App';
import CommonIconButton from '../ui/CommonIconButton';


function DepartmentScreen() {

  //Get state from auth context
  let {token, isAuthenticated} = useContext(AuthContext);

  //Get state for department from redux
  let {departments} = useSelector((state : RootState) => state.department);

  //Set navigation
  let navigation = useNavigation<StackNavigationProp<RootParamList>>();

  //Set effect to check authentication 
  useEffect(() => {
      if (!isAuthenticated || token == null) {
          navigation.navigate("LogInScreen");
      }
  },[isAuthenticated, token]);


  //Set layout effect to set header left

  useEffect(() => {

    navigation.setOptions({
      headerRight: ({tintColor}) => {
        return <CommonIconButton name="add-outline" color={tintColor as string} size={20} width={80} backgroundColor={colors.offWhite} onPress={handleToAddDepartmentPage}/>
      }
    });
  }, [navigation]);

  //Handle to add department page

  const handleToAddDepartmentPage = () => {
      navigation.navigate("AddOrUpdateDepartmentScreen", {});
  };

  //Set effect to fetch all departments

  useEffect(() => {
    dispatchStore(getAllDepartments(token as string) as any);
  },[token]);

  //Type cast array of departments
  let arrayDepartments = departments as Department[];

  //Handle render single department

  const handleRenderDepartments : ListRenderItem<Department> = ({item, index}) => {

    
    return <SingleDeparment {...item}/>
  }

  //Handle back to the previous page
  const handleBack = () => {
    navigation.goBack();
  }


  //Handle page when there are no departments in list

  if (arrayDepartments?.length === 0) {
    return (
      <View style={styles.screen}>
      <CommonIconButton name={"arrow-back-outline"} color={"black"} size={20} width={50} backgroundColor={colors.grey} onPress={handleBack}/>
      <Text style={styles.title}> Department List </Text> 

      <Text style={styles.errorText}> There are no departments in a list! </Text> 
   </View>
    )
  }
  

  return (
   <View style={styles.screen}>
      <CommonIconButton name={"arrow-back-outline"} color={"black"} size={20} width={50} backgroundColor={colors.grey} onPress={handleBack}/>
      <Text style={styles.title}> Department List </Text> 

      <View style={styles.list}>
        
        <FlatList data={arrayDepartments} renderItem={handleRenderDepartments} keyExtractor={(item : any, index : any) => {
          return item.id
        }}/>
      </View>
   </View>
  )
}

const styles = StyleSheet.create({
    screen: {
      flex: 1,
      backgroundColor: colors.skinColor,
      padding: 20
    },
    title: {
      textAlign: "center",
      fontWeight: "bold",
      marginVertical: 10,
      fontSize: 24
    },
    list: {
      marginBottom: 80
      
    },
    errorText: {
      textAlign: "center",
      fontSize: 24,
      marginVertical: 20,
      fontWeight: "bold",
      color: colors.red
    }
})

export default DepartmentScreen