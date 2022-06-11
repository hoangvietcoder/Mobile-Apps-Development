import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
} from "react-native"
import { Colors, colorList } from "../../constants/Colors"
import React, { useState } from "react"
import { CommonActions } from "@react-navigation/native"
import { ColorChoose } from "../../components/ColorChoose/ColorChoose"

export const Edit = ({ navigation, route }) => {
  const [title, setTitle] = useState(route.params.title)
  const [color, setColor] = useState(route.params.color)
  const [isValid, setValidity] = useState(true)

  return <View style={styles.container}>
    <View style={{flexDirection: 'column', }}>
        <Text style={styles.label}>List name</Text>
        <TextInput
          autoFocus={true}
          value={title}
          onChangeText={setTitle}
          placeholder={"Enter your list name..."}
          maxLength={30}
          style={styles.input}
        />
         {!isValid && (
            <Text
              style={{
                marginLeft: 4,
                color: Colors.red,
                fontSize: 12,
                marginTop: 10
              }}
            >
              * List Name cannot be empty
            </Text>
        )}
        <Text style={[styles.label, {marginTop: 10}]}>Choose Color</Text>
        <ColorChoose 
          selectedColor={color} 
          colorOptions={colorList} 
          onSelect={(color) => {
            setColor(color)
            navigation.dispatch(CommonActions.setParams({color}))
          }}
        />
    </View>
    <TouchableOpacity style={[styles.saveButton, { backgroundColor: route.params.color}]} onPress={() => {
      if(title.length > 1) {
        route.params.saveChanges({title, color})
        navigation.dispatch(CommonActions.goBack())
      } else {
        setValidity(false)
      }
    }}>
      <Text style={{color: 'white', fontSize: 24, fontWeight: 'bold'}}>Save</Text>
    </TouchableOpacity>
  </View>
}

const styles = StyleSheet.create({
  container: {
      flex: 1,
      backgroundColor: "#fff",
      padding: 15,
      justifyContent: "space-between",
  },
  input: {
      color: Colors.darkGray,
      borderBottomColor: Colors.lightGray,
      borderBottomWidth: 0.5,
      marginHorizontal: 5,
      padding: 3,
      height: 30,
      fontSize: 24,
  },
  saveButton: {
      borderRadius: 15,
      height: 48,
      margin: 16,
      justifyContent: "center",
      alignItems: "center",
  },
  label: {
      color: Colors.black,
      fontWeight: "bold",
      fontSize: 18,
      marginBottom: 8,
  },
});