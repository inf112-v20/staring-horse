# <p align="center">Obligatorisk Oppgave 2</p>

## Deloppgave 1: Prosjekt og prosjektstruktur

### Retrospektiv og vurdering av iterasjonen

#### Roller, arbeidsfordeling og gruppedynamikk

Rollefordelingen i teamet fungerer bra, kundekontakt Terje har fått enda tydeligere ansvar for å ta spørsmål til gruppeleder ved at han noterer ned en liste med spørsmål vi lurer på, istedenfor at alle spør underveis når ting dukker opp. Terje har også hatt ansvar for UML diagram og fått kodet enda mer i denne iterasjonen. Kristine fortsetter som teamlead, hun har hatt oversikt over møtetidspunkt, rombooking, satt opp og fulgt opp brukerhistorier og oppgaver på project board og sikret at gruppen holder seg til de oppgavene som er satt og vet hva de skal jobbe med om de er usikre, og ellers skrevet rapporter og referat. Vi beholder Olav og Johan med hovedansvar for koden, de skal sikre at den er sammenhengende og klar til innleveringene, de har dessuten til nå gjort en kjempeinnsats med å skrive mesteparten av koden og kommet opp med kodeløsninger. 

Ellers føler vi at dynamikken i gruppen er fin, kommunikasjon via Slack og møtene er bra, og alle bidrar så godt de kan der de kan. Dette kommer enda ikke helt tydelig frem av commits da mesteparten av koden har blitt commitet fra samme datamaskin pga par/gruppe-programmeringen og tideligere arbeid i prosjektet. 
Ps. Commits fra Kristine.G.Mo og Kristine89 er samme person. Skal prøve å merge brukerene for å samle alle hennes commits på en bruker.

#### Metodikk og arbeidsmåte

Vi føler at metodikken vi har valgt med kombinasjonen av Kanban og Scrum fungerer bra, og gir oss den friheten vi trenger for å kunne prøve oss frem i arbeidet, men også et bra rammeverk å holde oss innenfor, slik som tenkt og forklart i første innlevering. I tillegg til møter med parkoding for å dele kunnskap og ideer, vi vil også sette av tid til koding på egenhånd da dette ser ut til å være litt mer effektivt for gruppemedlemmene, og gir oss enda bedre mulighet for å legge opp arbeidet i forhold til timeplanene våre og hvilke tidspunkt vi jobber best; for noen er det gjerne på morgenene, for andre ettermiddag/kveld. Her vil vi at alle skal få utnytte sine styrker, i tillegg til at vi fortsetter å utfordre oss selv med samarbeid.

Project board og issues på Git har fungert veldig bra for oss, det har gjort at vi har hatt god oversikt på arbeidsoppgaver, og alle i gruppen har satt pris på å ha konkrete, «bite-size» oppgaver å jobbe ut i fra. Alle har vært flinke til å holde seg innenfor oppgavene på tavlen, selv om det har vært en utfordring at man lett kan «skli ut» og tenke på kode som ikke er viktig i den aktuelle iterasjonene. Dette kommer nok av hvordan vi er vant til å jobbe som studenter fra før, der vi gjerne har kodet mest mulig på kortest mulig tid uten så mye planlegging på forhånd. Prosjektet gir oss god trening i hvordan å holde fokus og jobbe organisert. Utfyllende referat har også vært til hjelp for å tydeliggjøre hva som er gjennomgått og hva vi ble enige om å jobbe med til neste møte. Dette gjelder spesielt om gruppemedlemmer ikke er tilstede på møter, men også om man trenger en liten påminnelse.

Til nå i prosjektet synes vi at arbeidsmengden vi har satt opp har vært passelig, og vi har fått løst oppgavene i stor grad innenfor den tiden vi har til rådighet. Det har tatt en del tid å sette seg inn i de nye verktøyene som LibGDX og Tiled, og bruke de sammen med det vi kan om objekt orientert programmering, og vi har derfor ikke oppnådd optimal kodedesign. Vi har også brukt en del tid på å lage brukerhistorier og sette oss inn i prosjektmetodikk, da dette er et viktig læringsmål i faget.
 
####  Forbedringspunkt

Det første forbedringspunktet vi har bestemt oss for er å få en jevnere fordeling av kodeoppgaver. De som er best på koding ønsker å bli enda flinkere til å dele kunnskap om koding med de i gruppen som ikke er like kunnskapsrike. Helt konkret vil vi sette opp ett-to ekstra ansikt-til-ansikt møter i løpet av iterasjonene med dette som formål.      

Vårt andre forbedringspunkt vil være å ha stor fokus på å forbedre kodedesignet med gruppediskusjoner, veiledning fra gruppeleder og å lære enda mer om gamedesign via nettet, nå når vi har kommet bedre inn i arbeidsmåten og funnet ut hva som fungerer for teamet.  
 

## Deloppgave 2: Krav

#### Brukerhistorier med arbeidsoppgaver og akseptansekriterier vi har jobbet med denne iterasjonen:

**1.** Som spiller må jeg kunne bevege roboten på brettet basert på instruksjoner fra programkort.

_Arbeidsoppgaver:_ Implementere klasse for programkort med instrukser tilsvarende brettspillet om bevegelser på brettet. Disse skal endre på robot-klassens tilstand slik at roboten flytter seg rundt på brettet.

_Akseptansekriteriet:_ Robot kan beveges med instrukser fra minst ett programkort.

**2.** Som spiller vil jeg se programkort på skjermen for å ha oversikt over hvordan roboten skal bevege seg/vite hvordan roboten skal bevege seg på brettet.

_Arbeidsoppgaver:_ Designe utseende og velge plassering av kort i forhold til spillbrettet – inspirert av brettspillet. Gjøre de synlig på skjermen for å gi en fin visuell opplevelse av spillet.

_Akseptansekriteriet:_ Fullført når programkort er synlig på skjermen med valgt design.

**3.** Som spiller må jeg kunne velge 5 programkort i den rekkefølgen jeg ønsker for å bevege roboten på spillbrettet.

_Arbeidsoppgaver:_ Implementere muligheten for å velge 5 vilkårlige programkort som spilleren kan sette i ønsket rekkefølge.

_Akseptansekriteriet:_ Fullført når spilleren kan velge og sette opp 5 programkort i prioritert rekkefølge.
 
**4.** Som spiller vil jeg se vegger på brettet for å planlegge rekkefølgen på bevegelsene mine.

_Arbeidsoppgaver:_ Legge til vegger på brettet. Bruke Tiled for å lage Wall layer.
Viktig: Vegger ligger mellom to felt på brettet.

_Akseptansekriteriet:_ Fullført når vegger er synlige på brettet
 
**5.** Som spiller vil jeg at roboten min skal bli stoppet av vegger for å øke vanskelighetsgraden i spillet og gjøre det mer utfordrende å vinne.

_Arbeidsoppgaver:_ Implementere at roboten blir stoppet av vegger. 

_Akseptansekriteriet:_ Fullført når roboten ikke kan bevege seg gjennom (minst én type) vegg.


Bildet under viser et eksempel på hvordan project boardet vårt ser ut i løpet av iterasjonen.

![projectBoardOblig2](https://github.com/inf112-v20/staring-horse/blob/master/Deliverables/Images/Staring-horse%20mid-iteration%20project%20board.png)

 
#### Prioritering fremover

Det vi vil prioritere fremover og i neste iterasjon er å implementere (ikke prioritert rekkefølge):

- Mulighet for å vinne, tape og dø
- Implementere runder og faser
- Plukke flere flagg i riktig rekkefølge for å vinne
- Lasere
- Menu-screen før spillet starter (som vi senere vil at skal gi mulighet for å velge brett, evt regler, vanskelighetsgrad m.m)

Vi har fått til bevegelse med programkort, fått til brett og en robot på brettet, vegger som hindrer bevegelse, flagg den kan nå for å vinne og hull den kan falle i som skal føre til at den dør. Basert på hvordan vi satte opp og spilte det fysiske brettspillet, føler vi at vi har implementert det som trengs for å spille spillet og at det er tid for å implementere muligheten for å faktisk gjennomføre runder og faser. Da må man også ha mulighet for å vinne, tape og dø. Lasere er også noe som vi vil ha med for å gjøre spillet mer spennende og utfordrende. En menu-screen er et designvalg vi har tatt basert på gruppemedlemmenes tidligere spillerfaring, som gir spilleren mer kontroll over hvordan han/hun vil spille. Vi vil ikke at spilleren skal bli «kastet» inn i spillet uten å få velge brett, regler osv.

#### MVP listen og endringer

Etter å ha spilt det fysiske brettspillet valgte vi å flytte noen av høynivåkravene våre til Nice-to-have kravlisten, da vi følte at man uten disse fortsatt har et fungerende spill. Ellers jobber vi videre med høynivåkravlisten slik den står i backlog. Det vi fjernet fra listen var:

- når nest siste spiller er ferdig skal stoppeklokken på 30 sek settes i gang
- spiller annonserer powerdown
- robot kan være i powerdown
- aktivere robot fra powerdown

#### Bugs i kravene vi har utført

Når vi skalerer skjermen til fullskjerm, skalerer ikke knappene, i.e. programkortene, seg til tilsvarende størrelse. Dette gjør det vanskelig å skjønne hvor på skjermen man skal trykke for å velge kort, da knappen ikke lenger er på selve kortene. Dette skal vi jobbe videre med i neste iterasjon.

Spiller kan stå på bakgrunnsbrettet og gå utenfor brettet. Dette skal i fremtiden fikses ved at spilleren skal respawne etter å gå av brettet.

Når spiller står på flagg eller hull skrives det veldig mange ganger til konsollen. Dette vil fikses når vi får på plass at spilleren kan dø og vinne.

### UML Diagram Oblig 2

![oblig2-uml](https://github.com/inf112-v20/staring-horse/blob/master/Deliverables/Images/oblig2-uml.PNG)


### Linker til møtereferat fra denne iterasjonen

**[Møtereferat 19. februar(1)](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-5,-19.-februar-klokken-0800)**

**[Møtereferat 19. februar(2)](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-6,-19.-februar-klokken-1400)**

**[Møtereferat 24. februar](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-7,-24.-februar)**

**[Møtereferat 25. februar](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-8,-25.februar)**

**[Møtereferat 26. februar](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-9,-26.februar)**

**[Møtereferat 27. februar](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-10,-27.-februar)**
