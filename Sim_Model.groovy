//! Simulation

import org.arl.fjage.RealTimePlatform
import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import org.arl.unet.phy.Ranging

///////////////////////////////////////////////////////////////////////////////
// simulation settings

platform = RealTimePlatform           // use real-time mode

///////////////////////////////////////////////////////////////////////////////
// simulation details
 

simulate {
  node '1', remote: 1101, address: 1, location: [0.km, 0.km,  -5.m], stack: { container ->
    container.add 'transmit',new EnergyModel()
    container.add 'get',new EnergyModel()
    container.add 'ranging', new org.arl.unet.phy.Ranging()
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
    container.shell.addInitrc "${script.parent}/fshrc1.groovy"
  }, shell: true
  
  node '2', remote: 1102, address: 2, location: [1.km, 0.km, -10.m], stack: { container ->
    container.add 'ranging', new org.arl.unet.phy.Ranging()
    container.add 'transmit',new EnergyModel()
    container.add 'get',new EnergyModel()
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
    container.shell.addInitrc "${script.parent}/fshrc1.groovy"
  }, shell: 5102



  node '3', remote: 1103, address: 3, location: [1.km, 1.km, -15.m], stack: { container ->
    container.add 'ranging', new org.arl.unet.phy.Ranging()
     container.add 'transmit',new EnergyModel()
      container.add 'get',new EnergyModel()
   container.shell.addInitrc "${script.parent}/fshrc.groovy"
   container.shell.addInitrc "${script.parent}/fshrc1.groovy"
  }, shell: 5103

     
  node '4', remote: 1104, address: 4, location: [0.5.km, 1.km, -10.m], stack: { container ->
    container.add 'ranging', new org.arl.unet.phy.Ranging()
     container.add 'transmit',new EnergyModel()
      container.add 'get',new EnergyModel()
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
    container.shell.addInitrc "${script.parent}/fshrc1.groovy"
  }, shell: 5104
  
  node '5', remote: 1105, address: 5, location: [0.km, 1.km, -10.m], stack: { container ->
    container.add 'ranging', new org.arl.unet.phy.Ranging()
     container.add 'transmit',new EnergyModel()
      container.add 'get',new EnergyModel()
      container.shell.addInitrc "${script.parent}/fshrc.groovy"
      container.shell.addInitrc "${script.parent}/fshrc1.groovy"
  }, shell: 5105
  
}






