import { Component, AfterViewInit } from '@angular/core';
import { Http } from '@angular/http';
import { MyObj } from './interfaces';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements AfterViewInit {

  dataUrl = 'http://127.0.0.1:8080';
  jsonMaps: MyObj
  mapSize: string;
  obj: MyObj;

  constructor(private http: Http) { }

  ngAfterViewInit() {
    this.startClient();
  }

  public startClient() {
    this.http.get(this.dataUrl).subscribe(
      data => {
        this.jsonMaps = data.json();
        this.mapSize = String((640 / Math.sqrt(this.jsonMaps[0].nodes.length)));
      },
      error => {
        console.log(error)
      }
    );
  }

  public isNumeric(n: any): n is number | string {
    return !isNaN(parseFloat(n)) && isFinite(n);
  }

  public getStyle() {
    return "20"
  }
}
