package com.example.denis.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    boolean keepReading = true;
    String[] cpu0 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
    String[] cpu1 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq"};
    String[] cpu2 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq"};
    String[] cpu3 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq"};
    String[] cpu4 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu4/cpufreq/scaling_cur_freq"};
    String[] cpu5 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu5/cpufreq/scaling_cur_freq"};
    String[] cpu6 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu6/cpufreq/scaling_cur_freq"};
    String[] cpu7 = {"/system/bin/cat", "/sys/devices/system/cpu/cpu7/cpufreq/scaling_cur_freq"};
    String[] maxFreq = {"/system/bin/cat", "/sys/devices/system/cpu/cpu4/cpufreq/cpuinfo_max_freq"};
    //String[] temp = {"/system/bin/cat", "sys/class/hwmon/hwmonX/temp1_input"};
    String[] temp = {"/system/bin/cat", "sys/devices/virtual/thermal/thermal1_zone0/temp"};
    String[] batterytemp = {"/system/bin/cat", "/sys/class/power_supply/battery/temp"};
    String[] thermal = {"/system/bin/cat", "/sys/devices/platform/tmu/temperature"};
    String[] cputemp = {"/system/bin/cat", "sys/class/thermal/thermal_zone0/temp"};
    //cpu temp is here! sys/class/thermal/thermal_zone0/temp > might be zone 0-8 pretty similar
    //use adb shell cat sys/class/thermal/thermal_zone0/temp to read file

    String pattern = "([0-9]{6,7})";
    Pattern pat = Pattern.compile(pattern);
    String patter2 = "([0-9]{1,2})";
    Pattern pat2 = Pattern.compile(patter2);

    int foo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor TempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorManager.registerListener(temperatureSensor,TempSensor,SensorManager.SENSOR_DELAY_NORMAL);


        final TextView tv_core0 = (TextView) findViewById(R.id.core0freq);
        final TextView tv_core1 = (TextView) findViewById(R.id.core1freq);
        final TextView tv_core2 = (TextView) findViewById(R.id.core2freq);
        final TextView tv_core3 = (TextView) findViewById(R.id.core3freq);
        final TextView tv_maxFreq = (TextView) findViewById(R.id.tv_maxFreq);
        //final TextView tv_cpu2file = (TextView) findViewById(R.id.tv_cpu2file);
        final TextView tv_cputemp = (TextView) findViewById(R.id.tv_cputemp);




        final File cpu0file = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
        final File cpu1file = new File("/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq");
        final File cpu2file = new File("/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq");
        final File cpu3file = new File("/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq");
        final File cpu4file = new File("/sys/devices/system/cpu/cpu4/cpufreq/scaling_cur_freq");
        final File cpu5file = new File("/sys/devices/system/cpu/cpu5/cpufreq/scaling_cur_freq");
        final File cpu6file = new File("/sys/devices/system/cpu/cpu6/cpufreq/scaling_cur_freq");
        final File cpu7file = new File("/sys/devices/system/cpu/cpu7/cpufreq/scaling_cur_freq");

        final File cputempfile = new File("sys/class/thermal/thermal_zone0/temp");
        final File maxcpufreqfile = new File("/sys/devices/system/cpu/cpu4/cpufreq/cpuinfo_max_freq");

        if (maxcpufreqfile.exists()) {
            tv_maxFreq.setText(formatCPUFreq(ReadCPU0(maxFreq)) + " Mhz");
        } else tv_maxFreq.setText("N/A");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (keepReading) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (cpu0file.exists()) {
                                    tv_core0.setText(formatCPUFreq(ReadCPU0(cpu0))+ " MHz");
                                } else {
                                    tv_core0.setText("Offline");
                                }

                                if (cpu1file.exists()) {
                                    //tv_cpu2file.setText("Yes");
                                    tv_core1.setText(formatCPUFreq(ReadCPU0(cpu1))+ " MHz");
                                } else {
                                    //tv_cpu2file.setText("No");
                                    tv_core1.setText("Offline");
                                }

                                if (cpu2file.exists()) {
                                    tv_core2.setText(formatCPUFreq(ReadCPU0(cpu2))+ " MHz");
                                } else {
                                    tv_core2.setText("Offline");
                                }

                                if (cpu3file.exists()) {
                                    tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3))+ " MHz");
                                } else {
                                    tv_core3.setText("Offline");
                                }

                                if (cpu4file.exists()) {
                                    //tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3))+ " MHz");
                                    System.out.println("CPU 4: " + formatCPUFreq(ReadCPU0(cpu4)) + " MHz");
                                } else {
                                    //tv_core3.setText("Offline");
                                    System.out.println("CPU 4: " + formatCPUFreq(ReadCPU0(cpu4)) + " MHz");
                                }

                                if (cpu5file.exists()) {
                                    //tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3))+ " MHz");
                                    System.out.println("CPU 5: " + formatCPUFreq(ReadCPU0(cpu5)) + " MHz");
                                } else {
                                    //tv_core3.setText("Offline");
                                    System.out.println("CPU 5: " + formatCPUFreq(ReadCPU0(cpu5)) + " MHz");
                                }

                                if (cpu6file.exists()) {
                                    //tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3))+ " MHz");
                                    System.out.println("CPU 6: " + formatCPUFreq(ReadCPU0(cpu6)) + " MHz");
                                } else {
                                    //tv_core3.setText("Offline");
                                    System.out.println("CPU 6: " + formatCPUFreq(ReadCPU0(cpu6)) + " MHz");
                                }

                                if (cpu7file.exists()) {
                                    //tv_core3.setText(formatCPUFreq(ReadCPU0(cpu3))+ " MHz");
                                    System.out.println("CPU 7: " + formatCPUFreq(ReadCPU0(cpu7)) + " MHz");
                                } else {
                                    //tv_core3.setText("Offline");
                                    System.out.println("CPU 7: " + formatCPUFreq(ReadCPU0(cpu7)) + " MHz");
                                }

                                if (cputempfile.exists()) {
                                    //tv_cputemp.setText(ReadCPU0(cputemp));
                                    tv_cputemp.setText(formatTemp(ReadCPU0(cputemp)));
                                } else {
                                    tv_cputemp.setText("N/A");
                                }

                                //tv_cpu2file.setText(ReadCPU0(temp));
                                //System.out.println("Battery " + ReadCPU0(batterytemp));
                                //System.out.println("Thermal " + ReadCPU0(thermal));

                                foo = Integer.parseInt(formatTemp(ReadCPU0(cputemp)));
                                //System.out.println(formatTemp(ReadCPU0(cputemp)));
                                //System.out.println("foo is " + foo);
                                if (foo < 40) {
                                    tv_cputemp.setTextColor(getResources().getColor(R.color.Blue));
                                }

                                if (foo > 40 && foo < 50) {
                                    tv_cputemp.setTextColor(getResources().getColor(R.color.Yellow));
                                }
                                if (foo > 50 && foo < 60) {
                                    tv_cputemp.setTextColor(getResources().getColor(R.color.Brown));
                                }
                                if (foo > 60) {
                                    tv_cputemp.setTextColor(getResources().getColor(R.color.Red));
                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private String ReadCPU0(String[] input)
    {
        ProcessBuilder pB;
        String result = "";

        try{
            //String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
            pB = new ProcessBuilder(input);
            pB.redirectErrorStream(false);
            Process process = pB.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1) //default -1
            {
                //System.out.println(new String(re));
                result = new String(re);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }

    private String formatCPUFreq (String cpuFreq) {

        String uwot = "";
        Matcher m = pat.matcher(cpuFreq);
        if (m.find()) {
            uwot = m.group(0);
            return uwot.substring(0, uwot.length() - 3);
        } else return "Error";
    }

    private String formatTemp (String cpuFreq) {

        String uwot = "";
        Matcher m = pat2.matcher(cpuFreq);
        if (m.find()) {
            uwot = m.group(0);
            return uwot.substring(0, uwot.length() - 0);
        } else return "0";
    }

    private SensorEventListener temperatureSensor = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            System.out.println("on sensor changed called");
            float temp = event.values[0];
            System.out.println("Temperature sensor: " + temp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    protected void onDestroy() {
        super.onDestroy();
        keepReading = false;
    }

    protected void onStop() {
        super.onStop();
        //keepReading = false;      //don't disable otherwise stops reading after going to sleep
    }

    protected void onResume() {
        super.onResume();
        keepReading = true;
    }



}
