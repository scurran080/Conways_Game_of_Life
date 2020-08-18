import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Conways_Game_of_Life extends PApplet {

//Sean Curran
//This is an older project I made while looking into processing.
//Press 'p' to pause/play, 'r' to reset, 'Left Mouse Button' to create/destroy

int cellSize =15;
float startAliveProb = 20;

int interval = 90;
int lastRecTime = 0;

int alive = color(0,160,180);
int dead = color(0);
int[][] cells;
int[][] cellBuff;
boolean pause = false;

public void setup(){
  
  cells = new int[width/cellSize][height/cellSize];
  cellBuff = new int[width/cellSize][height/cellSize];
  stroke(50);
  //noStroke();
  
  for(int x = 0; x < width/cellSize; x++){
    for(int y = 0; y < height/cellSize; y++){
      float rState = random(100);
      if(rState > startAliveProb){
        rState = 0;
      }
      else{
        rState = 1;
      }
      cells[x][y] = (int)rState;
    }
  }
  background(0);
}

public void draw(){
  for(int x = 0; x < width/cellSize; x++){
    for(int y = 0; y < height/cellSize; y++){
      if(cells[x][y] == 1){
        fill(alive);
      }
      else{
        fill(dead);
      }
      rect(x*cellSize,y*cellSize,cellSize,cellSize);
      if(millis()-lastRecTime > interval){
        if(!pause){
          iterate();
          lastRecTime = millis();
        }
      }
    }
  }
  if(mousePressed){
    int newCellX = PApplet.parseInt(map(mouseX,0,width,0,width/cellSize));
    int newCellY = PApplet.parseInt(map(mouseY,0,height,0,height/cellSize));
    newCellX = constrain(newCellX,0,width/cellSize-1);
    newCellY = constrain(newCellY,0,height/cellSize - 1);
    if(cellBuff[newCellX][newCellY] == 1){
      cells[newCellX][newCellY] = 0;
      fill(dead);
    }
    else{
      cells[newCellX][newCellY] = 1;
      fill(alive);
    }
  }
  //save to cellBuff
  if(pause && !mousePressed){
    for(int x = 0; x < width/cellSize; x++){
      for(int y = 0; y < height/cellSize; y++){
        cellBuff[x][y] = cells[x][y];
      }
    }
  }
}

public void keyPressed(){
  //pause
  if(key == 'p' || key == 'P'){
    pause = !pause;
  }
  //clear all
  if(key == 'c' || key == 'C'){
    for(int x = 0; x < width/cellSize; x++){
      for(int y = 0; y < height/cellSize; y++){
        cells[x][y] = 0;
        cellBuff[x][y] = cells[x][y];
      }
    }
  }
  //reset
  if(key == 'r' || key =='R'){
    for(int x = 0; x < width/cellSize; x++){
      for(int y =0; y < height/cellSize; y++){
        float rState = random(100);
        if(rState > startAliveProb){
          rState = 0;
          cells[x][y] = PApplet.parseInt(rState);
        }
        else{
          rState = 1;
          cells[x][y] = PApplet.parseInt(rState);
        }
        
      }
    }
  }
}

//loop through each cell and check neighbors and setting value based on count of neighbors
public void iterate(){
  for(int x = 0; x < width/cellSize; x++){
    for(int y = 0; y < height/cellSize; y++){
      cellBuff[x][y] = cells[x][y];
    }
  }
  for(int x = 0; x < width/cellSize; x++){
    for(int y = 0; y < height/cellSize; y++){
      int count = 0;
      for(int xx = x - 1; xx <= x+1; xx++){
        for(int yy = y - 1; yy <= y+1; yy++){
          if (((xx>=0)&&(xx<width/cellSize))&&((yy>=0)&&(yy<height/cellSize))) { 
            if (!((xx==x)&&(yy==y))) {
              if (cellBuff[xx][yy]==1){
                count ++;
              } //fi
            }//fi
          }//la done
        }
      }//lo done
      if(cellBuff[x][y] == 1){
        if(count < 2 || count > 3){
          cells[x][y] = 0;
        }
      }
      else{
        if(count == 3){
          cells[x][y] = 1;
        }
      }
    }
  }
}
  public void settings() {  size(800,800);  noSmooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Conways_Game_of_Life" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
