import { StyleSheet } from "react-native"
import { Colors } from "../../constants/Colors"

export const styles = StyleSheet.create({
  container: {
      flex: 1,
      backgroundColor: Colors.white,
      position: 'relative'
  },
  box: {
    width: 300,
    height: 300,
    backgroundColor: "red",
    marginBottom: 30,
  },
  addIcon: {
      position: 'absolute',
      bottom: 40,
      right: 20,
      zIndex: 100,
      backgroundColor: Colors.primary,
      width: 60,
      height: 60,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      borderRadius: 50,
      shadowColor: "#000",
      shadowOffset: {
          width: 0,
          height: 5,
      },
      shadowOpacity: 0.34,
      shadowRadius: 6.27,
      
      elevation: 10,
  },
  itemTitle: { fontSize: 20, padding: 2, color: "white" },
  itemContainer: {
      flexDirection: "row",
      justifyContent: "space-between",
      alignItems: "center",
      height: 70,
      flex: 1,
      borderRadius: 20,
      marginHorizontal: 20,
      marginVertical: 10,
      padding: 15,
      backgroundColor: Colors.blue
  },
  icon: {
      padding: 5,
      fontSize: 24,
  },
  centeredView: {
      justifyContent: "center",
      alignItems: "center",
      marginTop: 50,
  },
  modalView: {
      backgroundColor: "white",
      borderRadius: 20,
      padding: 35,
      alignItems: "center",
      shadowColor: "#000",
      shadowOffset: {
          width: 0,
          height: 2,
      },
      shadowOpacity: 0.25,
      shadowRadius: 3.84,
      elevation: 5,
  },
});
