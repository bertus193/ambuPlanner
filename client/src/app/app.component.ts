import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { MyObj } from './interfaces';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

    dataUrl = 'http://127.0.0.1:8080';
    jsonMaps: MyObj
    obj: MyObj;

    public width: Number;
    public height: Number;

    constructor(private http: Http) { }

    ngOnInit() {
        this.startClient();
    }

    public startClient() {
        this.http.get(this.dataUrl).subscribe(
            data => {
                this.jsonMaps = data.json();
                console.log(this.jsonMaps)
                this.width = this.calculateWidthSize() * 45 - 20
                this.height = (this.calculateHeightSize() * 45) - 20

            },
            error => {
                console.log(error)
            }
        );
    }

    public isNumeric(n: any): n is number | string {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }

    public calculateWidthSize() {
        let nodes = this.jsonMaps[0].nodes;
        let out = nodes.length
        for (let i = 0; i < nodes.length; i++) {
            if (nodes[i].coordValue.x == 1 && nodes[i].coordValue.y == 0) {
                return i + 1;
            }
        }
        return out
    }

    public calculateHeightSize() {
        let nodes = this.jsonMaps[0].nodes;
        let out = nodes.length
        let count = 1;
        for (let i = 0; i < nodes.length; i++) {
            if (nodes[i].coordValue.y == 0) {
                count++;
            }
        }
        return count
    }
}
