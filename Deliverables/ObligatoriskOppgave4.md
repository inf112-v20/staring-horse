# <p align="center">Obligatorisk Oppgave 4</p>

## Deloppgave 1: Team og Prosjekt

### Retrospektiv og erfaringer fra prosjektet

Fortsatt fungerer gruppen bra i forhold til rollefordeling og arbeidsfordeling, og vi sitter igjen med følelsen av at vi har løst team-arbeidet så godt vi kunne etter evnene til teammedlemmene og i forhold til hva som fungerer best for oss. Den største utfordringen har vært å kombinere arbeidet med andre fag, og fordele arbeidet helt jevnt, da noen har en travlere timeplan enn andre og noen har bedre kodeevner enn andre. Men vi har hatt god kommunikasjon rundt dette fra start, og sikret at alle er enige om fordelingen og ingenting føles urettferdig, vi har prøvd å basere oss på styrkene til de ulike medlemmene slik at opplevelsen med prosjektarbeidet skulle bli en bra erfaring for alle. Kommunikasjonen mellom oss har vært fin fra start, men har etter hvert blitt mer og mer lettbent og vi har allerede en del «inside-jokes» som vi har det morro med på møtene. Dette hjelper på idémyldring, gode diskusjoner og ved at vi rett og slett koser oss mer med arbeidet, noe som vi føler i sin tur definitivt har gitt mer arbeidslyst og dermed bedre resultater. En utfordring i forhold til kommunikasjon har vært å nå hverandre utenfor avtalte tidspunkt siden vi har kommunisert via Slack, og man gjerne ikke sjekker det like ofte og til samme tidspunkt som hverandre slik man for eksempel ville gjort i en jobbsituasjon der alle har arbeidsdagen sin i løpet av samme tidsrom. 
Det å ha prøvd ut prosjektmetodikk og teknikker (spesifikt Scrum og Kanban i vårt tilfelle) i praksis i et programvareutviklings-prosjekt har vært veldig lærerikt, og som Siv lærte oss i forelesning, skjønner vi i mye større grad hvorfor det har blitt utviklet egne metodikker rettet mot programvarebransjen. Måten å jobbe på er mye mer frem og tilbake, og innebærer mye mer endringer underveis enn gruppemedlemmene har erfart i andre prosjekter, så det er viktig med godt organisert arbeid.

Det viktigste vi har lært
Noe av det viktigste vi har lært er hvordan å organisere arbeidet og jobbe sammen i et nytt team i et større prosjekt. Hvordan man best kommuniserer - over hvilke kanaler, det å være tydelig, og å sikre at man bruker samme ord for å beskrive samme konsept osv, - og å finne styrkene til team-medlemmene og bruke de, for å sikre en effektiv og god gjennomføring av oppgavene. 
Vi har lært mye mer enn vi kunne om tekniske aspekter som Git, GitHub, Codacy, Travis og spillutvikling med libGDX. Vi føler at vi har fått god forståelse for brukerhistorier, project-board og verdien det har, og det å ha en prosjektmetodikk å arbeide etter generelt for å ha struktur og oversikt.
Og ikke minst, vi har lært å spille Robo-Rally! 

#### Nedstengingen av universitetet

I forhold til nedstengningen av universitetet ble den største forskjellen å ta møter over Discord istedenfor ansikt-til-ansikt. Ellers hadde vi en arbeidsprosess som fungerte bra remote også; vi brukte i større grad Google Docs for å skrive brukerhistorier i fellesskap før iterasjonen slik at alle enkelt kunne skrive «en setning her og en setning der» om man kom på gode formuleringer. Prosjekt-tavlen fortsatte å hjelpe oss å holde oversikt over arbeidsfordeling og fremdriften til de ulike arbeidsoppgavene, siden alle ble veldig flinke til å flytte over det de jobbet med og forsikre seg om at andre på gruppen hadde flyttet sine issues over i riktige kolonner.
 
En ting som også ble litt annerledes var at parprogrammeringen ble litt mer ensidig, i forhold til at en og en person streamet og skrev kode om gangen; man kan ikke dele tastatur på samme måte og bytte på kodingen like hyppig som når man er i samme rom - det blir upraktisk i forhold til pushing og pulling osv.
En positiv erfaring var at det sparte oss for tid og det ble lettere å kombinere med andre fag og møter man måtte ha, siden det bare er å logge seg på Discord så er man på plass. 
I tillegg er vi glad for at vi virkelig har fått testet det å jobbe remote, det er en viktig erfaring å ha med seg videre i studie- og arbeidslivet.

#### Hva ville vi gjort annerledes

Viss vi skulle gjort noe annerledes ville vi ha planlagt kodeløsningene i forhold til abstraksjon osv. om mulig enda bedre for å unngå endel endringer som vi måtte jobbe med mot slutten. Og vi ville prøvd oss enda mer på testdrevet utvikling for bli bedre på den fremgangsmåten.
Men, alt i alt har prosjektarbeidet fungert veldig bra, samarbeid og innsatsen fra alle har vært topp, og det har vært veldig lærerikt å jobbe sammen på denne måten, og vi har alle fått utfordret oss på flere måter, både teknisk og organisatorisk.

#### Prosjektboard mid-iteration

![projectBoardOblig4](https://github.com/inf112-v20/staring-horse/blob/master/Deliverables/Images/Staring-horse-mid-iteration-projectboard-oblig4.png)



## Deloppgave 2: Krav

I denne siste iterasjonen har vi jobbet med følgende brukerhistorier basert på kravene fra MVP listen. I tillegg har vi gjennomført en del flere elementer ved spillet, blant annet de kravene vi i forrige innlevering hadde forespeilet som Nice to Have krav (markert):


**1.** Som spiller vil jeg ha faser og runder i spillet for å gjøre spillet mer likt brettspillet.

_Arbeidsoppgaver:_ Implementer en “game-loop” som kjører fasene Activation Phase og Programming Phase, og runder basert på regelboka fra brettspillet 

_Akseptansekriterie:_ Fullført når man kan gjennomføre faser og runder slik som regelboken sier.
(Regelbok til inspirasjon: https://media.wizards.com/2017/rules/roborally_rules.pdf )


**2.** Som spiller vil jeg at roboten skal kunne tape spillet mot en annen spiller for å få et spennende spill.

_Arbeidsoppgave:_ Implementere at spilleren taper hvis en annen robot når alle flaggene først.

_Akseptansekriterier:_ Fullført når spilleren taper spillet og ikke får spille lenger når en annen robot vinner spillet.


**3.** Som spiller vil jeg få beskjed om jeg vinner eller taper for å vite når dette skjer.

_Arbeidsoppgaver:_ Design og implementer at det kommer tydelig frem når man har vunnet eller tapt et spill ved at et pop-up vindu popper opp på skjermen med informasjon om tap eller seier. Implementer også at man fra denne pop-up'en kan velge å spille det samme brettet på nytt eller gå tilbake til hovedmenyen.

_Akseptansekriterie:_ Fullført når man som spiller tydelig får beskjed når man har vunnet eller tapt spillet.


**4.** Som spiller vil jeg at programkort skal bli låst etter antall skade roboten tar for å øke vanskelighetsgraden til spillet.

_Arbeidsoppgaver:_ Implementere at programkort blir låst/utilgjengelige å velge når spilleren tar skade basert på de samme reglene som brettspillet.

_Akseptansekriterier:_ Fullført når programkort blir utilgjengelig å velge etter hvor mye skade roboten tar.


**5.**	Som spiller vil jeg kunne dytte andre spillere for å sabotere de andre spillerne

_Arbeidsoppgaver:_ Implementere at når en robot går inn i samme rute som en annen robot dyttes roboten en rute i samme retning som den første roboten kom fra. Om man når vegger stoppes bevegelsen.

_Akseptansekriterier:_ Fullført når en robot kan bli dyttet én rute av en annen robot.


**6.**	Som spiller vil jeg at roboten skal skyte av en laser i retningen den peker for å sabotere de andre spillerne.

_Arbeidsoppgaver:_ Implementere at roboter avfyrer laser i retningen den peker på slutten av hvert utførte programkort.

_Akseptansekriterier:_ Fullført når alle robotene på brettet avfyrer lasere for hvert utførte programkort.


**7.**	Som spiller vil jeg ha en AI å spille mot for å gjøre spillet mer spennende.

_Arbeidsoppgaver:_ Implementere AI-klasse som har samme oppførsel som en robot i spillet.

_Akseptansekriterier:_ Ferdig når AI oppfører seg som spillerens robot, men tar automatiske trekk.


**8.** (Nice to have) Som spiller vil jeg ha tannhjul som roterer roboter for å gjøre brettet mer komplekst.

_Arbeidsoppgaver:_ Implementere et visuelt tannhjul på brettet, samt funksjonalitet.

_Akseptansekriterier:_ Fullført når en robot roteres når den lander på tannhjul. Rødt roterer 90 grader til venstre, grønt roterer 90 grader til høgre.


**9.** (Nice to have) Som spiller vil jeg ha samlebånd som flytter på roboter for å gi spilleren mer faktorer å tenke på.

_Arbeidsoppgaver:_ Implementere et visuelt samlebånd på brettet, og la det flytte roboter i den retningen samlebåndet har. Vanlig samlebånd flytter roboter 1 felt frem, express samlebånd flytter roboten 2 felt frem.

_Akseptansekriterier:_ Fullført når en robot blir flyttet minst ett steg når den er på et vanlig samlebånd og 2 steg når den er på et express samlebånd.


**10.** (Nice to have) Som spiller vil jeg at roboten min skal få økt hp når den har vært innom en skiftenøkkel for å kunne reparere skade og ha bedre sjanse for å vinne.

_Arbeidsoppgaver:_ Implementer synlige skiftenøkler på spillbrettet og at roboter kan få skade reparert av å stå på en skiftenøkkel. Økningen skjer på slutten av hvert utførte programkort i aktiveringsfasen.

_Akseptansekriterier:_ Fullført når robotens hp øker med 1 når den har vært oppå en skiftenøkkel-tile.


**11.**	(Nice to have) Som spiller vil jeg at flaggene skal fungere som skiftenøkler og respawn-points for å gjøre spillet mer likt brettspill-versjonen.

_Arbeidsoppgaver:_ Legg til at flagg også fungerer som skiftenøkkel og respawn-point.

_Akseptansekriterier:_ Fullført når robotene kan få hp økt med 1 når de står på flagg, og når de kan respawne etter død på det siste besøkte flagget.


**12.**	(Nice to have) Som spiller ønsker jeg å se informasjon om roboten min for å ha bedre oversikt over hvordan spillet går.

_Arbeidsoppgaver:_ Implementer at informasjon om antall liv, hp, flagg, posisjon og retning roboten peker er synlig for spilleren på skjermen når de spiller.

_Akseptansekriterie:_ Fullført når spilleren kan se informasjonen som er ønsket på skjermen mens de spiller.


**13.**	(Nice to have) Som spiller vil jeg ha muligheten til å velge hvilke brett jeg vil spille for å ha forskjellige vanskelighetsgrader og for at spillet skal appellere til flere typer spillere.

_Arbeidsoppgaver:_ Implementer "map-selector" på meny skjermen.

_Akseptansekriterie:_ Fullført når spilleren kan velge mellom minst to brett av ulik vanskelighetsgrad på meny skjerm før spillet starter.


**14.**	(Nice to have) Som spiller ønsker jeg å velge vanskelighetsgrad på motstander AI for å ha flere valgmuligheter i spillet.

_Arbeidsoppgaver:_ Forbedre AI slik at den gjør smartere trekk og har flere vanskelighetsgrader.

_Akseptansekriterie:_ Fullført når man kan velge to ulike vanskelighetsgrader på motstander AI.


**15.**	(Nice to have) Som spiller vil jeg ha prioritering på programkortene for at spillet skal fungere som brettspillet.

_Arbeidsoppgaver:_ Legg til prioritering på programkortene slik som prioriteten er gitt i brettspillet.

_Akseptansekriterier:_ Fullført når alle typer programkort har fått riktig prioritetsnummer basert på slik det er i brettspillet.



I tillegg til disse konkrete brukerhistoriene dukket det opp flere «små-oppgaver» som vi løste parallelt med brukerhistoriene.

•	Forbedret designet på meny skjerm, la til mulighet for å lese regelbok.

•	La til «activate-cards» knapp for å gjøre det tydeligere når programmeringsfasen er over og aktiveringsfasen starter.

•	Fikset visuelle bugs i forhold til resolution, utseende på og plassering av elementer som kort, tekst osv.

•	Generell refaktorering underveis for å forbedre koden, i tillegg til å ha enda større fokus på å skrive tester.


I denne iterasjonen har vi løst mange flere brukerhistorier og krav enn de forrige. Dette var mulig både fordi vi alle hadde bedre tid siden andre fag i stor grad var ferdig med undervisning og oppgaveinnleveringer, og fordi det ble enklere og enklere å legge til nye features når kodebasen vår ble større.

#### Bugs i koden

## Deloppgave 3

Se hvordan prosjektet bygges og kjøres i ReadMe-filen

Manuelle tester finnes på egen wiki-side

Klassediagram

### Møtereferat fra iterasjonen

**[Møtereferat 1. april](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-20,-1.-April)**

**[Møtereferat 15. april](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-21,-15.-april)**

**[Møtereferat 22. april](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-22,-22.-april)**

**[Møtereferat 29. april](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-23,-29.-april)**

**[Møtereferat 30. april](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-24,-30.-april)**

**[Møtereferat 1. mai](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-25,-01.mai)**

**[Møtereferat 4. mai](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-26,-4.-mai)**

**[Møtereferat 5. mai](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-27,-5.-mai)**

**[Møtereferat 6. mai](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-28,-6.mai)**

**[Møtereferat 7. mai](https://github.com/inf112-v20/staring-horse/wiki/M%C3%B8te-29,-7.mai)**



