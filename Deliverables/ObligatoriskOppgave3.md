# <p align="center">Obligatorisk Oppgave 3</p>

## Deloppgave 1: Team og prosjekt

### Retrospektiv

I denne iterasjonen endret vi etter oppfordring fra gruppeleder rollene i teamet. Som nevnt i første rapport var dette tanken fra start; at vi i løpet av prosjektet ville bytte på roller, og gjennom hele prosjektet gi alle litt ansvar for alt, slik at alle på teamet fikk prøve seg med forskjellige ansvarsområder og for å se hva som fungerte best og hvilke roller gruppemedlemmene naturlig «gikk inn i». Det viste seg at rollene vi definerte og fordelte fra begynnelsen av traff bra; vi fikk utnyttet styrkene til teammedlemmene og ting fungerte bra; når vi byttet møtte vi på litt utfordringer med å få samme gode flyten og arbeidsfordeling. Covid-19 utfordret oss også en del når vi plutselig måtte gjøre alt arbeid remote og kun kommunisere digitalt via Slack og Discord. Mengden møter gikk ned, og gruppemedlemmene jobbet mer hver for seg enn med parprogrammering, da dette fungerte bedre for de fleste og ble mer tidseffektivt i den nye hverdagen og i kombinasjon med at arbeidsmengde økte i andre fag hos alle teammedlemmene. Kommunikasjonen fungerte fortsatt fint, selv om regelen om å sjekke Slack 1 gang for dagen gjerne ble litt «glemt», noe som er forståelig når hverdagen og rutinene endret seg i så stor grad. Som ofte med forandringer tar det litt tid å komme inn i ting og Covid-19 situasjonen har gjort det utfordrende for alle på teamet, men alle i gruppen er klare for siste innsats og fortsetter å yte sitt beste etter tid og evne.

Oppsummert føler vi at vi har funnet kommunikasjonskanaler og verktøy som kommer til å fungere bra (Slack og Discord), og det har vært veldig lærerikt å jobbe remote, det er nyttig kunnskap som vi er glad for å ta med oss videre og bli enda bedre på i neste iterasjon. Prosjektmetodikken fungerer bra og vil forbli den samme (Scrum og Kanban i kombinasjon). Igjen har spesielt project board og tiden vi brukte på å planlegge brukerhistorier og arbeidsoppgaver hjulpet oss veldig med å holde fokus og ha oversikt.

#### Forbedringspunkter:

**1.** Komme ordentlig inn i rutinen med å jobbe remote og finne tilbake til den jevne kommunikasjonen og arbeidsflyten i den nye hverdagen.

**2.** Legge til flere møter for å kunne diskutere løsninger og hjelpe hverandre.

#### Prioriterte oppgaver videre

I den neste og siste iterasjonen vil prioriteten ligge på å ferdigstille og forbedre koden fra kravene vi har begynt på i denne iterasjonen; spesifikt de som har med å kunne vinne og tape og runder og faser, slik at vi ender opp med et spill som faktisk fungerer å spille. 
De brukerhistoriene det gjelder spesifikt har vi lagt klar i To Do kolonnen. Når vi jobbet med brukerhistorie #7 (se i krav-delen under) oppdaget vi at vi måtte dele opp arbeidsoppgaven og akseptansekriteriet for at den ikke skulle bli for stor siden vi enda ikke har en motspiller.


«Skjermdump av projectboard»

## Deloppgave 2: Krav

Brukerhistorier vi satte opp til denne innleveringen handlet i stor grad om å implementere muligheten for å vinne og tape, og visse elementer som henger sammen med dette i forhold til brettspillets regler. Helt konkret, disse kravene fra mvp-listen:

- Mulighet for å vinne, tape og dø
- Plukke flere flagg i riktig rekkefølge for å vinne
- Lasere
- Menu screen

#### Brukerhistorier:

**1.** Som spiller vil jeg ha en meny-skjerm for å kunne ha valgmuligheter før jeg eventuelt starter spillet slik at jeg ikke kastes direkte inn i spillet.

_Arbeidsoppgave:_ Velge design og implementere en menyskjerm.

_Akseptansekriterier:_ Fullført når spilleren kan ha valgmulighetene til å starte spillet eller avslutte programmet fra menyen.



**2.** Som spiller vil jeg at roboten skal ha mulighet til å vinne spillet for å ha noe å jobbe mot og for å få et fungerende, spennende spill.

_Arbeidsoppgaver:_ Implementere at flagg kan besøkes i en viss rekkefølge slik at roboten kan vinne spillet.

_Akseptansekriterier:_ Fullført når èn robot kan vinne ved å ha besøkt alle flaggene i riktig
rekkefølge først.  



**3.** Som spiller vil jeg ha lasere ut fra vegger på brettet for å øke vanskelighetsgraden til spillet.

_Arbeidsoppgaver:_ Designe utseende til laser basert på slik den ser ut i brettspillet/Tiled, og gjøre den synlig på brettet. Implementere at lasere stopper når den treffer andre vegger eller roboter.

_Akseptansekriterier:_ Fullført når minst én laser er synlig på brettet og laseren blir stoppet av vegg eller robot.   



**4.** Som spiller vil jeg at roboten skal ta skade når den blir truffet av en laser for å gjøre spillet mer utfordrende.

_Arbeidsoppgaver:_ Implementere at roboten sin hp blir redusert når den går inn i en laserstråle.

_Akseptansekriterier:_ Fullført når roboten sin hp blir redusert med 1 når den blir truffet av laser.



**5.** Som spiller vil jeg at roboten skal miste liv når den faller i hull, går utenfor brettet eller når den har mistet alle hp for et gitt liv, for å øke risikofaktoren i spillet.

_Arbeidsoppgaver:_ Implementere at robotens antall liv blir redusert når den står på hull, går utenfor brettet eller når alle hp er brukt opp for ett liv.

_Akseptansekriterier:_ Fullført når robotens antall liv blir redusert når den går på hull tiles, utenfor brettet eller hp er redusert til 0.



**6.** Som spiller vil jeg kunne ta backup av roboten for å kunne starte på nytt hvis den faller utenfor brettet eller går i et hull.

_Arbeidsoppgaver:_ Implementere "tile" på brettet som fungerer som backup point, der roboten kan restarte om den har flere liv igjen.

_Akseptansekriterier:_ Fullført når roboten kan starte på nytt på backup-tile på brettet hvis den har flere liv igjen.



**7.** Som spiller vil jeg at roboten skal kunne tape spillet for å få et fungerende spill og for å drive spilleren til å ville vinne spillet.

_Arbeidsoppgave:_ Implementere at roboten taper hvis den mister alle liv, eller hvis en annen robot besøker alle flaggene og vinner spillet

_Akseptansekriterier:_ Fullført når roboten taper spillet og ikke får spille lenger når den taper alle liv eller en annen robot vinner spillet.

I tillegg manglet vi å ferdigstille kravet om at vegger stopper robot fra forrige iterasjon, så denne ble også jobbet videre med (ligger i In Progress kolonnen). Denne iterasjonen ble ikke som forventet, og arbeidsmengden vi hadde satt oss viste seg å bli litt for stor. Prioritet videre vil som nevnt over være å fokusere på selve spillets gang og å ferdigstille brukerhistoriene vi er i gang med. Vi ser at realistisk sett kan det være vi må fjerne noen flere krav fra mvp (se hvilke vi vurderer å fjerne under), men vi lar den stå som den er nå og vil ta en ny vurdering mot midten av iterasjonen.

- Implementere tannhjul som roterer robot.
- Implementere at roboten kan få reparert skade med skiftenøkkel.
- Implementere samlebånd som beveger roboter.


## Deloppgave 3: Produktleveranse og kodekvalitet

Vi hadde et problem med at testene krasjet når man refererte til GameScreen fra Player (for å finne ut om spilleren har gått av brettet eller i et hull). Dette løste vi ved å ha en boolean Player.isTestPlayer som man setter til true (Player.setToTestPlayer()) på spilleren som brukes til testing. Dette er mest sannsynligvis kun en midlertidig løsning som skal bli endret på i neste iterasjon (dette medfører større endringer i kode-strukturen som vi ikke føler er realistisk å gjennomføre før tidsfristen), men vi følte løsningen var nødvendig for å kunne fortsette å jobbe med brukerhistoriene vi har prioritert til denne iterasjonen.

#### UML-diagram

![Oblig3-uml](https://github.com/inf112-v20/staring-horse/blob/master/Deliverables/Images/Oblig3_uml.png)


## Møtereferat

**[Møtereferat 2. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-11,-2.mars)**

**[Møtereferat 4. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-12,-4.-mars)**

**[Møtereferat 11. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-13,-11.-mars)**

**[Møtereferat 16. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-14,-16.-mars)**

**[Møtereferat 18. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-15,-18.-mars)**

**[Møtereferat 24. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-16,-24.mars)**

**[Møtereferat 25. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-17,-25.-mars)**

**[Møtereferat 26. mars](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-18,-26.-mars)**

**[Møtereferat 27. mars]()**
