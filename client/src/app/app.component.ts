import { Component, OnInit } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
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
    }

    public exampleClient() {
        this.http.get(this.dataUrl + "/example/").subscribe(
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

    public createMap() {
        var mapJSON = (<HTMLInputElement>document.getElementById('mapJSON')).value
        var notificationsJSON = (<HTMLInputElement>document.getElementById('notificationsJSON')).value
        if (this.IsJsonString(mapJSON) && this.IsJsonString(mapJSON)) {
            /*let data = new URLSearchParams();
            data.append('mapJSON', mapJSON);
            data.append('notificationsJSON', notificationsJSON);*/
            var re = /"/gi;
            var mapJSON = mapJSON.replace(re, "\\\"");

            let body = '{ "mapJSON": "' + mapJSON + '", "notificationsJSON": "' + notificationsJSON + '"}';

            let headers = new Headers({ 'Content-Type': 'application/json' });
            let options = new RequestOptions({ headers: headers });

            this.http.post(this.dataUrl + '/generateMap', body, options)
                .subscribe(data => {
                    this.jsonMaps = data.json();
                    this.width = this.calculateWidthSize() * 45 - 20
                    this.height = (this.calculateHeightSize() * 45) - 20
                }, error => {
                    console.log(error.json());
                });
        } else {
            console.log("error");
        }
    }

    public IsJsonString(str) {
        try {
            JSON.parse(str);
        } catch (e) {
            return false;
        }
        return true;
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
