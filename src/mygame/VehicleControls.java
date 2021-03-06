/*
 * Created by Udit Jindal, IMT2015043
 * Module: User controls definitions.
 * Created on: 8/11/2016
 */
package mygame;
import vehicles.*;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;
import com.jme3.math.Matrix3f;

/**
 *
 * @author udit
 */
public class VehicleControls implements ActionListener {
    
    private final String vehicleName;
    private final Vehicle vehicle; 
    private float accelerationValue;
    private float steeringValue;
    private final float accelerationPower;
    private float jumpPower;
    int BreakPower;
    InputManager inputManager;
    public VehicleControls(String name ,Vehicle vehicle, float accelerationPower, 
             InputManager inputManager){
        this.vehicle= vehicle;
        this.accelerationPower= accelerationPower;
        this.inputManager= inputManager;
        this.vehicleName= name;
    }
    
    //This function manages the keyboard, or mouse based event, and it listens for input every frame.
    public void setupKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("RearLook", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
        inputManager.addListener(this, "Reset");
        inputManager.addListener(this, "Reverse");
        inputManager.addListener(this, "RearLook");
    }
    
    //When a key is pressed, it has a value 1 or 0, saying whether it is pressed or not, and so accordingly we take actions.
    //Such as accelerate the car, or brake, or turn left or right, based on the corresponding keyboard strokes.
    @Override
    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("Lefts")) {
            if (value) {
                steeringValue += 0.5f;
            } else {
                steeringValue += -.5f;
            }
            
            vehicle.getController().steer(steeringValue);
        } 
        if (binding.equals("Rights")) {
            if (value) {
                steeringValue += -.5f;
            } else {
                steeringValue += .5f;
            }
            vehicle.getController().steer(steeringValue);
        } 
        if (binding.equals("Ups")) {
            if (value) {
                accelerationValue -= accelerationPower;
            } else {
                accelerationValue += accelerationPower;
            }
            vehicle.getController().accelerate(accelerationValue);
            vehicle.getController().setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(vehicle.getGeometryOfNode(vehicle.getCarNode(), vehicleName)));
        }
        if (binding.equals("Reverse")) {
            if (value) {
                accelerationValue += accelerationPower;
            } else {
                accelerationValue -= accelerationPower;
            }
            vehicle.getController().accelerate(accelerationValue);
            vehicle.getController().setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(vehicle.getGeometryOfNode(vehicle.getCarNode(), vehicleName)));
        }
        if (binding.equals("Downs")) {
            if (value) {
                vehicle.getController().brake(310f);
            } else {
                vehicle.getController().brake(0f);
            }
        } 
        if (binding.equals("Space")) {       
            if (value)
                System.out.println(vehicle.getCarNode().getLocalTranslation());
        }
        if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                vehicle.getController().setPhysicsLocation(new Vector3f(-20f, 18f,-2f));
                vehicle.getController().setPhysicsRotation(new Matrix3f());
                vehicle.getController().setLinearVelocity(Vector3f.ZERO);
                vehicle.getController().setAngularVelocity(Vector3f.ZERO);
                vehicle.getController().resetSuspension();
            } else {
            
            }
        }
        if (binding.equals("RearLook")) {
            if (value) {
                vehicle.rearLook = true;
            }
            else {
                vehicle.rearLook = false;
            }
        }
    }
}
