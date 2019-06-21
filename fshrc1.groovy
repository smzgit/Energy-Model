import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.fjage.Message

// documentation for the 'get' command
doc['get'] = '''get - get data from a remote node

Examples:
  get             // receive data from a remote node
  
'''
subscribe phy
get = {
  
    System.out.println "Waiting........"
           
 }
 
