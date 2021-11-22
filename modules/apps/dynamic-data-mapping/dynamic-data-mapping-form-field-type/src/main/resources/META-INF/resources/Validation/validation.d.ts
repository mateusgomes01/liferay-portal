interface IParameters {
    dateFieldName?: string;
	quantity: number;
	date: DateType;
	unit: Unit;
	type: Type;
}

interface IParametersProperties {
	dateFieldName?: string;
	quantity?: number;
	date?: DateType;
	unit?: Unit;
	type?: Type;
}

type DateType = 'customDate' | 'responseDate' | 'dateField';
type Unit = 'days' | 'months' | 'years'
type Type = 'customDate' | DateType;