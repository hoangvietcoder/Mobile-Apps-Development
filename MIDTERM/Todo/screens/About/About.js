import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Image
} from "react-native"
import { Colors } from "../../constants/Colors"
import Avatar from '../../assets/avatar.jpg'

export const About = () => {
  return <View style={styles.container}>
    <View style={styles.item}>
      <View style={styles.left}>
        <Image source={Avatar} style={styles.image}/>
      </View>
      <View style={styles.right}>
        <Text style={styles.text}>Phan Duy Tâm</Text>
        <Text style={styles.text}>MSSV: 51900827</Text>
        <Text style={styles.text}>Khoa: Công nghệ thông tin</Text>
        <Text style={styles.text}>Lớp: 19050402</Text>
      </View>
    </View>
    <View style={styles.item}>
      <View style={styles.left}>
        <Image source={Avatar} style={styles.image}/>
      </View>
      <View style={styles.right}>
        <Text style={styles.text}>Lê Quốc Trường</Text>
        <Text style={styles.text}>MSSV: 51900677</Text>
        <Text style={styles.text}>Khoa: Công nghệ thông tin</Text>
        <Text style={styles.text}>Lớp: 19050402</Text>
      </View>
    </View>
    <View style={styles.item}>
      <View style={styles.left}>
        <Image source={Avatar} style={styles.image}/>
      </View>
      <View style={styles.right}>
        <Text style={styles.text}>Mai Hoàng Việt</Text>
        <Text style={styles.text}>MSSV: 51900847</Text>
        <Text style={styles.text}>Khoa: Công nghệ thông tin</Text>
        <Text style={styles.text}>Lớp: 19050402</Text>
      </View>
    </View>
  </View>
}


export const styles = StyleSheet.create({
  container: {
      flex: 1,
      display: 'flex',
      alignItems: 'center',
      backgroundColor: Colors.white,
      padding: 20

  },
  item: {
    borderColor: Colors.primary,
    borderWidth: 4,
    width: 350,
    height: 200,
    marginVertical: 20,
    display: "flex",
    flexDirection: 'row',
    borderRadius: 10
  },
  left: {
    flex: 2,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  },
  right: {
    flex: 3,
    padding: 20
  },
  text: {
    paddingVertical: 8,
    fontSize: 15
  },
  image: {
    borderRadius: 50,
    width: 100,
    height: 100
  }
})