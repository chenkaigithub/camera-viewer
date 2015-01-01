package az.baku.emin.cameraviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView.BufferType;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	Button settings_done;
	Spinner resolution_spinner;
	EditText width_input;
	EditText height_input;
	
	EditText address_input;
	EditText port_input;
	EditText command_input;
	
	RadioGroup port_group;
	RadioGroup command_group;
	
	int width = 640;
	int height = 480;
	

	int ip_port = 80;
    String address;
	String ip_command = "?action=stream";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        Bundle extras = getIntent().getExtras();
		
        ArrayAdapter<CharSequence> adapter =
        		ArrayAdapter.createFromResource(this, R.array.resolution_array,
        									android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
        resolution_spinner = (Spinner) findViewById(R.id.resolution_spinner);
        resolution_spinner.setAdapter(adapter);
		
        width_input = (EditText) findViewById(R.id.width_input);
        height_input = (EditText) findViewById(R.id.height_input);
		

        address_input = (EditText) findViewById(R.id.address_input);
        port_input = (EditText) findViewById(R.id.port_input);
        command_input = (EditText) findViewById(R.id.command_input);
        
        port_group = (RadioGroup) findViewById(R.id.port_radiogroup);
        command_group = (RadioGroup) findViewById(R.id.command_radiogroup);
        
        if(extras != null){
        	width = extras.getInt("width", width);
        	height = extras.getInt("height", height);
			
        	address = extras.getString("address");
        	ip_port = extras.getInt("ip_port", ip_port);
        	ip_command = extras.getString("ip_command");
    		
        	width_input.setText(String.valueOf(width));
        	height_input.setText(String.valueOf(height));
        	resolution_spinner.setSelection(adapter.getCount()-1);
			
        	address_input.setText(String.valueOf(address));

        	port_input.setText(String.valueOf(ip_port));
        	command_input.setText(ip_command);
        }

        resolution_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){  
			public void onItemSelected(AdapterView<?> parent, View viw, int arg2, long arg3) {  
				Spinner spinner = (Spinner)parent;  
				String item = (String)spinner.getSelectedItem();
				if(item.equals("640x480")){
					width = 640;
					height = 480;
				}else if(item.equals("480x640")){
					width = 480;
					height = 640;
				}else if(item.equals("320x240")){
					width = 320;
					height = 240;
				}else if(item.equals("240x320")){
					width = 240;
					height = 320;
				}else if(item.equals("176x144")){
					width = 176;
					height = 144;
				}else if(item.equals("144x176")){
					width = 144;
					height = 176;
				}
				width_input.setText(String.valueOf(width));
				height_input.setText(String.valueOf(height));
            }  
            public void onNothingSelected(AdapterView<?> parent) {  
            }
        }); 


        
        port_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
                if(checkedId == R.id.port_80){
                	port_input.setText(getString(R.string.port_80));
                }else if(checkedId == R.id.port_8080){
                	port_input.setText(getString(R.string.port_8080));
                }
            }
        });
        
        command_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
                if(checkedId == R.id.command_streaming){
                	command_input.setText(getString(R.string.command_streaming));
                }else if(checkedId == R.id.command_videofeed){
                	command_input.setText(getString(R.string.command_videofeed));
                }
            }
        });
        
        settings_done = (Button)findViewById(R.id.settings_done);
        settings_done.setOnClickListener(
        		new View.OnClickListener(){
        			public void onClick(View view){     
        				
        				String s;
        				
        				s = width_input.getText().toString();
        				if(!"".equals(s)){
        					width = Integer.parseInt(s);
        				}
        				s = height_input.getText().toString();
        				if(!"".equals(s)){
        					height = Integer.parseInt(s);
        				}
        				s = address_input.getText().toString();
        				address = s;
        				
        				s = port_input.getText().toString();
        				if(!"".equals(s)){
        					ip_port = Integer.parseInt(s);
        				}
        				
        				s = command_input.getText().toString();
        				ip_command = s;
        				
        				Intent intent = new Intent();
        				intent.putExtra("width", width);
        				intent.putExtra("height", height);
        				intent.putExtra("address", address);
        				intent.putExtra("ip_port", ip_port);
        				intent.putExtra("ip_command", ip_command);
        	        
        				setResult(RESULT_OK, intent);
        				finish();
        			}
        		}        		
        	);
	}	
}
