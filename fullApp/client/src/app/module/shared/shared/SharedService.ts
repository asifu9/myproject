
import { Injectable } from '@angular/core';

import { Observable,Subject } from "rxjs";


@Injectable()
export class SharedService {
showModal:Subject<any> = new Subject();
public showModal$: Observable<any> = this
    .showModal.asObservable();
}