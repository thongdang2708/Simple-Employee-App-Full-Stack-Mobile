
import React from 'react';
import colors from '../constants/colors';
import {View, Text, Pressable, StyleSheet} from "react-native";

interface ButtonProps {
    children: React.ReactNode,
    onPress: () => void,

}

function ButtonForUserScreen({children, onPress} : ButtonProps) {
  return (
    <View style={styles.outerButtonContainer}>
        <Pressable onPress={onPress} style={({pressed}) => pressed && styles.pressedButton} android_ripple={{color: colors.offWhite}}>
            <View style={[styles.innerButtonContainer]}>
            <Text style={styles.text}> {children} </Text>
            </View>
        </Pressable>
    </View>
  )
};

const styles = StyleSheet.create({
    outerButtonContainer: {
        marginVertical: 10,
        borderRadius: 4,
        borderWidth: 1.5,
        elevation: 4,
        shadowColor: "grey",
        shadowOffset: {width: 0, height: 2},
        shadowRadius: 4,
        shadowOpacity: 0.35,
        minWidth: 150
    },
    text: {
        textAlign: "center",
        fontWeight: "bold"
    },
    innerButtonContainer: {
        padding: 5,
        backgroundColor: colors.yellow
    },
    pressedButton: {
        opacity: 0.35
    },
});


export default ButtonForUserScreen