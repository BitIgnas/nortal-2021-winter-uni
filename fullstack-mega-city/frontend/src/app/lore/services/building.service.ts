import { RefreshService } from './refresh.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError as observableThrowError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { BuildingRequest } from '../model/building-request';
import { BuildingResponse } from '../model/building-response';
import { environment } from 'src/environments/environment';

@Injectable({
	providedIn: 'root',
})
export class BuildingService {
	private readonly baseUrl = environment.baseUrl;

	constructor(
		private http: HttpClient,
		private refreshService: RefreshService
		) {}

	getAll(): Observable<BuildingResponse[]> {
		return this.http
		  .get<BuildingResponse[]>(this.baseUrl)
		  .pipe(map(data => data), catchError(this.handleError))
	}

	get(id: string): Observable<BuildingResponse> {
		return this.http
		  .get<BuildingResponse>(`${this.baseUrl}/${id}`)
		  .pipe(map(data => data), catchError(this.handleError))
	}

	post(data: BuildingRequest) {
		const headers = new Headers();
		headers.append('Content-Type', 'application/json');
		return this.http.post<BuildingResponse>(this.baseUrl, data);
	}

	put(data: BuildingRequest) {
		const headers = new Headers();
		headers.append('Content-Type', 'application/json');
		return this.http.put<BuildingResponse>(this.baseUrl, data).pipe(catchError(this.handleError));
	}

	delete(id: number) {
		const headers = new Headers();
		headers.append('Content-Type', 'application/json');
		return this.http.delete<BuildingResponse>(`${this.baseUrl}/${id}`)
		.pipe(
			tap(() => {
				this.refreshService.refresh();
			}),
			catchError(this.handleError)
		);
	}

	private handleError(res: HttpErrorResponse | any) {
		console.error(res?.error || res.body?.error);
		return observableThrowError(res.error || 'Server error');
	}
}


