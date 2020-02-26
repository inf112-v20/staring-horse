# <p align="center">Obligatorisk Oppgave 1: Prosjekt RoboRally – oppstart</p>

## Deloppgave 1: Organiser teamet
Alle i gruppen kjenner til og har skrevet kode i Java fra inf101 og inf102 kursene. Johan, Olav og Terje går datateknologi og har best kompetanse innen programmering i gruppen. Kristine går IKT, og har erfaring med og liker organisering, planlegging, design og kommunikasjon.

Av roller valgte vi Kristine som teamlead med hovedansvar for organisering av møter, referat, rapporter og project-board. Terje ble satt som kundekontakt med ansvar for å kommunisere med kunden/gruppeleder om spørsmål vi har til krav og prosjektet ellers. Olav og Johan fikk hovedansvar for koden og å se til at den blir konsekvent og fungerer optimalt. Ellers tenker vi at rollene skal være flytende gjennom prosjektet, slik at alle får prøve seg innen de ulike ansvarsområdene og får mulighet til å utvikle seg, og kan hjelpe til der det er behov.

## Deloppgave 2: Få oversikt over forventet produkt
Det overordnede målet for prosjektet er å utvikle en digital versjon av brettspillet RoboRally. Et spill der vi beveger roboter på et brett ved hjelp av programkort med retningsinstrukser, og samler et antall flagg raskest mulig for å vinne. Det må kunne spilles runder og faser, og det må være mulig å vinne og tape, enten ved at en robot når alle flaggene sine eller dør. Det skal kunne spilles med flere spillere over LAN, ha en graphic user interface og kunne fungere uavhengig av operativsystem.

**Høynivåkrav til systemet:**
* vise spillebrett
* vise robot
* vise flere roboter
* gjennomføre en fase
* spille en runde
* vinne spillet
* avslutte spillet
* kunne dele ut kort
* vise elementer på spillbrettet
* vise flagg på brettet
* besøke et flagg
* kunne utføre lovlig trekk
* robot kan få skade
* robot kan få reparert skade med skiftenøkkel
* robot kan miste et liv
* robot kan dø
* spiller kan programmere roboten med programkort
* godkjenne programkort-sekvens for runden
* prioritering på programmeringskort avgjør rekkefølge på robotens bevegelser i hver fase
* prioritering gjøres på nytt for hver fase
* antall kort justeres/reduseres basert på mengden skade roboten har
* robot kan fyre av laser
* når nest siste spiller er ferdig skal stoppeklokken på 30 sek settes i gang
* vise hull på brettet
* robot dør hvis den havner i hull
* robot dør hvis den havner utenfor brettet
* robot dør hvis den får 10 i skade
* vegger stopper bevegelse hos robot
* vegger stopper lasere
* vegger ligger mellom to felt på spillbrettet
* robot stopper laser
* en robot kan dytte en annen
* ta backup
* spiller annonserer powerdown
* robot kan være i powerdown
* aktivere robot fra powerdown
* samlebånd beveger robot
* tannhjul roterer robot

**Prioritert liste av krav til første innlevering:**
* Vise brett
* Plassere og vise elementer på brettet
* Plassere og vise robot

## Deloppgave 3: Velg og tilpass en prosess for gruppen
Vi bestemte oss for å jobbe utfra en kombinasjon av Scrum og Kanban metodikk. Dette gir oss et rammeverk å jobbe med som ikke er for låst, da vi enda ikke vet hva som fungerer best for prosjektet og gruppen. Elementene vi vil trekke ut spesifikt fra Scrum er hvordan gruppemedlemmene står friere til å organisere hvordan de jobber sammen eller løser oppgavene sine; siden vi er et nytt team synes vi det er greit å kunne prøve seg frem litt med hvordan vi løser oppgavene og tilpasse arbeidsmåten etter hvert. Når det er sagt, så ser vi for oss å bruke parprogrammering som en arbeidsmetode for å dele kunnskap og sikre at koden vi skriver er konsekvent, og at alle i gruppen får bidra så godt de kan.

Scrum begrenser oppgavene som skal løses i en iterasjon (sprint), mens i Kanban begrenser man antall oppgaver som skal løses samtidig. Dette håper vi vil hjelpe å holde oss fokusert og sikre at vi ikke tar på oss for mye arbeid ved å legge til nye ting underveis og ha for lange «in progress» lister på prosjekt tavlen vår. I Kanban jobber man også med kontinuerlig flyt som gir rom for endringer, dette vil være nyttig for oss som ferskt team for å kunne tilpasse arbeidsmengden underveis, siden vi ikke enda vet hvor mange oppgaver vi klarer å gjennomføre i en iterasjon.

I et slikt studentprosjekt ser vi at mesteparten av utfordringene vi møter handler om tid. Det å få nok tid til å arbeide med prosjektet når man må kombinere det med flere andre fag er ikke alltid enkelt. I tillegg går gruppemedlemmene på forskjellige studieretninger med ulike valgfag, og har dermed også ulike timeplaner. Dette gjør at møter ansikt til ansikt ikke alltid kan legges til tidspunkt som passer for alle, og at arbeid ikke alltid kan utføres samtidig; for noen passer det gjerne best tidlig i uken og formiddager, mens for andre er andre dager og tidspunkt bedre. Vi har foreløpig løst dette greit ved å legge møter til ettermiddager da dette er et tidspunkt som passer bra for gruppemedlemmenes timeplan.
Mangelen på erfaring med å jobbe på denne måten i prosjekt fører også med seg endel utfordringer i forhold til å vite hvilke løsninger som fungerer best. Alle gruppemedlemmene har som oftest jobbet alene med å skrive og levere kode tidligere. Her må vi prøve oss frem og endre fremgangsmåte etterhvert som det trengs, og i tillegg dra nytte av all teorien som finnes om de ulike agile metodene og erfaringer andre har lært av.

Som anbefalt kommer vi til å bruke github for å dele kode, dokumenter og lage en wiki med møtereferater. Ellers lager vi prosjekt-tavlen vår i github, der alle arbeidsoppgaver og brukerhistorier blir lagt inn. Tavlen deler vi inn i 5 kolonner:
1. 	Backlog: Alle krav og oppgaver vi skal løse i løpet av prosjektet (ikke satt opp i prioritert rekkefølge).
2. 	To do – iterasjon: Oppgavene vi har planlagt å løse i løpet av en iterasjon (prioritert rekkefølge).
3. 	In progress: Oppgaver som er påbegynt i iterasjonen (prioritert rekkefølge).
4. 	Ready for team review: Her legges oppgaver som skal godkjennes av hele teamet for at alle skal få si sin mening og vi får konsekvente løsninger, spesielt kodeoppgaver (prioritert rekkefølge).
5. 	Done: Oppgaver der akseptansekriterier er nådd og som er helt ferdige.

Organisering og booking av rom til møter tar teamlead ansvar for, og vi tar sikte på to-tre to timers møter i uken, inkl den obligatoriske gruppetimen, der vi får diskutert oppgaver og utfordringer ansikt til ansikt, og jobbet med parprogrammering for å utvikle koden til spillet. Møtereferater blir lagt i wiki på github. Individuelle arbeidsoppgaver blir fordelt på slutten av hvert møte, og nye møter blir avtalt enten i gruppetimen eller via Slack. Kommunikasjon mellom møter foregår også i Slack, i gruppen staring-horse. Vi har avtalt at alle sjekker Slack iallefall én gang for dagen, rett før lunsj, slik at man har tid å svare/løse problemstillinger på ettermiddagen. Teamlead har hovedansvar for å følge opp arbeid, men da vi har valgt å ha litt flytende roller vil det også være et feller ansvar i gruppen å hjelpe hverandre å holde oversikt og sikre at arbeidsoppgavene blir utført.

## Deloppgave 4: Kode og brukerhistorier
For å visualisere hvordan vi ville designe koden vår valgte vi å lage et UML-diagram. Diagrammet vi har laget i første omgang er ikke fullstendig, men fungerer bra som et startpunkt for å få en bedre oversikt over koden. UML-diagrammet kommer mest sannsynlig til å bli endret på over tid når vi finner andre løsninger. 

![oblig-uml](https://github.com/inf112-v20/staring-horse/blob/master/oblig-uml.PNG)

*Brukerhistorier for første iterasjon:*
1.    Som spiller trenger jeg å se et brett slik at jeg kan programmere roboten min.

*Akseptansekriterier:* Brett synlig på skjermen med 10x10 antall ruter, hver rute har basis utseende basert på brettspillet RoboRally.

*Arbeidsoppgaver:* Sette seg inn i LibGDX og Tiled. Designe strukturen til koden og implementere den.    
2.    Som spiller trenger jeg å se elementer på spillbrettet for å navigere roboten rundt elementene.

*Akseptansekriterier:* Vise minst ett hull og ett flagg plassert på brettet. Ha identifiserbart design på elementene slik at de kan skilles fra hverandre.

*Arbeidsoppgaver:* Velge design på hull og flagg basert på designet fra brettspillet. Designe strukturen til koden og implementere den.
3.    Som spiller trenger jeg å se roboten for å forstå hvor jeg kan bevege meg.

*Akseptansekriterier:* Vise roboten med (x,y) koordinater og retning.

*Arbeidsoppgaver:* Velge utseende til roboten. Designe og planlegge kode, og implementere den.

## Oppsummering og Retrospektiv

Vanskelig å komme godt i gang med arbeidet fordi det var utfordrende å forstå ideen om brukerhistorier og kravspesifikasjon. Her kunne gruppen som en helhet diskutert bedre og satt seg enda mer inn i teori for å lære og komme mer forberedt til møter. Vi skrev tre historier vi klarte å løse i stor grad i løpet av iterasjonen, uten at arbeidsmengden ble for stor, så her traff vi bra på mengden oppgaver i to-do kolonnen vår.

Det tok tid å sette seg inn i LibGDX og Tilde, og å kombinere dette med det man hadde lært om objekt orientert programmering i Java fra før var en utfordring. Vi føler derfor at kodedesignet vårt er et stykke fra optimal kvalitet og vil fokusere mye av kapasiteten vår på å forbedre dette i neste iterasjon.

Planlegging av møter og kommunikasjonen i gruppen fungerte bra, alle gjorde seg tilgjengelige og dedikerte den tiden vi trengte for å løse arbeidsoppgavene vi hadde satt oss. Vi vil fortsette å bruke Slack mellom møter, og ha to-tre møter i uken.

Arbeidsoppgaver vil vi definere enda tydeligere fremover slik at alle føler seg sikre på det de skal gjøre mellom hvert møte. Vi vil også ha tydeligere oversikt på hva man skal gå gjennom på møtene for å få mest mulig ut av tiden vår ansikt-til-ansikt. Dette vil vi løse ved å bruke 10 minutter før hvert møte på å gå gjennom hva vi har fått gjennomført mellom møtene, og hva vi basert på det, vil få gjort i løpet av møtet.
