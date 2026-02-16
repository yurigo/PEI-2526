# Sesión 05 - Actividad

**Fecha:** 16 de febrero de 2026

## Contenidos de la Sesión

En esta sesión se ha presentado el enunciado de la primera actividad.

### Enunciado

<h1 data-start="321" data-end="351">AC1 – Sensors biomètrics</h1>
<h2 data-start="352" data-end="400">Processament de mesures de sensors
  biomètrics</h2>
<p data-start="402" data-end="682">En un laboratori s’estan recollint mesures de
  sensors biomètrics (per exemple, <strong data-start="481"
    data-end="496">temperatura</strong>, <strong data-start="498"
    data-end="506">pols</strong>, <strong data-start="508"
    data-end="516">SpO₂</strong>). Disposem d’un fitxer <code data-start="540"
    data-end="553">mesures.csv</code> amb dades inicials que volem <strong
    data-start="583" data-end="626">llegir, llistar, representar en
    consola</strong> i <strong data-start="629" data-end="640">ampliar</strong>
  amb noves mesures introduïdes per teclat.</p>
<p data-start="684" data-end="753">Implementaràs la classe <code
    data-start="708" data-end="716">Mesura</code> i una aplicació de consola amb
  menú.</p>
<h3 data-start="760" data-end="782"> </h3>
<h3 data-start="760" data-end="782">1) Classe <code data-start="774"
    data-end="782">Mesura</code></h3>
<ul>
  <li data-start="783" data-end="1111">
    <p data-start="785" data-end="807"><strong data-start="785"
        data-end="804">Atributs mínims</strong>:</p>
    <ul>
      <li data-start="812" data-end="874"><code data-start="812"
          data-end="816">id</code> (enter) → <strong data-start="827"
          data-end="846">autoincremental</strong> i <strong data-start="849"
          data-end="857">únic</strong> per instància.</li>
      <li data-start="879" data-end="937"><code data-start="879"
          data-end="886">tipus</code> (String) → p. ex. <code data-start="905"
          data-end="918">TEMPERATURA</code>, <code data-start="920"
          data-end="926">POLS</code>, <code data-start="928"
          data-end="934">SPO2</code>.</li>
      <li data-start="942" data-end="987"><code data-start="942"
          data-end="949">valor</code> (double) → p. ex. 36.7, 72.0, 97.0.</li>
      <li data-start="992" data-end="1038"><code data-start="992"
          data-end="1000">unitat</code> (String) → p. ex. <code
          data-start="1019" data-end="1023">ºC</code>, <code data-start="1025"
          data-end="1030">bpm</code>, <code data-start="1032"
          data-end="1035">%</code>.</li>
      <li data-start="1043" data-end="1111"><code data-start="1043"
          data-end="1052">instant</code> (String) → format simple ISO, p. ex.
        <code data-start="1090" data-end="1108">2025-10-01 10:30</code>.</li>
    </ul>
  </li>
  <li data-start="1113" data-end="1469">
    <p data-start="1115" data-end="1131"><strong data-start="1115"
        data-end="1128">Requisits</strong>:</p>
    <ul>
      <li data-start="1136" data-end="1208">Control d’<strong data-start="1146"
          data-end="1168">id autoincremental</strong> amb un <strong
          data-start="1176" data-end="1194">camp de classe</strong> (<code
          data-start="1196" data-end="1204">static</code>).</li>
      <li data-start="1213" data-end="1236"><strong data-start="1213"
          data-end="1233">Dos constructors</strong>:
        <ul>
          <li data-start="1244" data-end="1295">Amb paràmetres (<code
              data-start="1260" data-end="1291">tipus, valor, unitat,
              instant</code>).</li>
          <li data-start="1303" data-end="1399">Que permeti construir a partir
            d’una línia del CSV (ignorant les línies que comencin per <code
              data-start="1392" data-end="1395">#</code>).</li>
        </ul>
      </li>
      <li data-start="1404" data-end="1469">Mètodes bàsics (<code
          data-start="1420" data-end="1432">toString()</code>, getters/setters
        amb validacions).</li>
    </ul>
  </li>
</ul>
<h3 data-start="1476" data-end="1510">2) Aplicació de consola (menú)</h3>
<p data-start="1511" data-end="1565">En engegar, el sistema carrega <code
    data-start="1542" data-end="1555">mesures.csv</code> i mostra:</p>
<p style="padding-left: 40px;"><code>■■■ Sensors ■■■</code><br><code>1. Llistar
    mesures</code><br><code>2.
    Representació ASCII</code><br><code>3. Afegir mesura</code><br><code>4.
    Buscar per tipus</code><br><code>5. Sortir</code></p>
<p style="padding-left: 40px;"><code>Escull una opció:</code></p>
<h4 data-start="1700" data-end="1712">Opcions</h4>
<ol data-start="1713" data-end="2952">
  <li data-start="1713" data-end="1842">
    <p data-start="1716" data-end="1779"><strong data-start="1716"
        data-end="1735">Llistar mesures</strong><br data-start="1735"
        data-end="1738">Mostra totes les mesures amb format:</p>
  </li>
</ol>
<p style="padding-left: 80px;"><code>■ &lt;id&gt;: &lt;tipus&gt; =
    &lt;valor&gt;&lt;unitat&gt; @ &lt;instant&gt;</code></p>
<ol data-start="1713" data-end="2952">
  <li data-start="1844" data-end="2355">
    <p data-start="1847" data-end="1890"><strong data-start="1847"
        data-end="1888">Representació ASCII (resum per tipus)</strong></p>
    <ul data-start="1894" data-end="2355">
      <li data-start="1894" data-end="1955">
        <p data-start="1896" data-end="1955">Es mostra un “gràfic de barres” per
          cada tipus de sensor.</p>
      </li>
      <li data-start="1959" data-end="2075">
        <p data-start="1961" data-end="1987">Es pot triar estratègia:</p>
      </li>
    </ul>
  </li>
</ol>
<ul>
  <li style="list-style-type: none;">
    <ul data-start="1894" data-end="2355">
      <li style="list-style-type: none;">
        <ul data-start="1894" data-end="2355">
          <li data-start="1995" data-end="2021">Últim valor registrat, o</li>
          <li data-start="2029" data-end="2075">Mitjana de totes les mesures
            d’aquell tipus.</li>
        </ul>
      </li>
      <li data-start="2079" data-end="2240">
        <p data-start="2081" data-end="2099">Escala senzilla:</p>
      </li>
    </ul>
  </li>
</ul>
<ul>
  <li style="list-style-type: none;">
    <ul>
      <li style="list-style-type: none;">
        <ul>
          <li data-start="2107" data-end="2151">TEMPERATURA (35–40 ºC → cada 0.5
            ºC = “#”)</li>
          <li data-start="2159" data-end="2198">POLS (40–180 bpm → cada 10 bpm =
            “#”)</li>
          <li data-start="2206" data-end="2240">SPO2 (80–100 % → cada 2 % = “#”)
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
<ul>
  <li style="list-style-type: none;">
    <ul data-start="1894" data-end="2355">
      <li data-start="2244" data-end="2355">
        <p data-start="2246" data-end="2256">Exemple:</p>
      </li>
    </ul>
  </li>
</ul>
<p style="padding-left: 120px;"><code>T: #### (36.8 ºC)</code><br><code>P:
    ### (68.5 bpm)</code><br><code>S: ######### (96 %)</code></p>
<ol data-start="1713" data-end="2952">
  <li data-start="2357" data-end="2602">
    <p data-start="2360" data-end="2379"><strong data-start="2360"
        data-end="2377">Afegir mesura</strong></p>
  </li>
</ol>
<ul>
  <li style="list-style-type: none;">
    <ul>
      <li data-start="2385" data-end="2443">Demana dades per teclat (tipus,
        valor, unitat, instant).</li>
      <li data-start="2449" data-end="2559">Valida: tipus vàlid (<code
          data-start="2470" data-end="2493">TEMPERATURA|POLS|SPO2</code>), valor
        numèric, unitat coherent, instant amb format plausible.</li>
      <li data-start="2565" data-end="2602">Assigna id automàtic i guarda al
        CSV.</li>
    </ul>
  </li>
</ul>
<ol data-start="1713" data-end="2952">
  <li data-start="2604" data-end="2907">
    <p data-start="2607" data-end="2629"><strong data-start="2607"
        data-end="2627">Buscar per tipus</strong></p>
  </li>
</ol>
<ul>
  <li style="list-style-type: none;">
    <ul>
      <li data-start="2635" data-end="2699">Demana un tipus i mostra <strong
          data-start="2660" data-end="2681">totes les mesures</strong> d’aquell
        tipus.</li>
      <li data-start="2705" data-end="2715">Exemple:</li>
    </ul>
  </li>
</ul>
<p style="padding-left: 120px;"><code>Tipus: TEMPERATURA</code><br><code>■ 1:
    TEMPERATURA = 36.7ºC @ 2025-09-30 10:15</code><br><code>■ 4: TEMPERATURA =
    37.2ºC @ 2025-09-30 11:00</code><br><code>■ 7: TEMPERATURA = 36.5ºC @
    2025-09-30 12:20</code></p>
<div class="overflow-y-auto p-4" dir="ltr" style="padding-left: 80px;"> </div>
<ol data-start="1713" data-end="2952">
  <li data-start="2909" data-end="2952">
    <p data-start="2912" data-end="2924"><strong data-start="2912"
        data-end="2922">Sortir</strong></p>
    <ul data-start="2928" data-end="2952">
      <li data-start="2928" data-end="2952">
        <p data-start="2930" data-end="2952">Finalitza l’aplicació.</p>
      </li>
    </ul>
  </li>
</ol>
<h3 data-start="3325" data-end="3352"> </h3>
<h3 data-start="3325" data-end="3352">3) Fitxer <code data-start="3339"
    data-end="3352">mesures.csv</code></h3>
<p data-start="3353" data-end="3362">Format:</p>
<p data-start="3353" data-end="3362" style="padding-left: 40px;">
  <code>id,tipus,valor,unitat,instant</code></p>
<p data-start="3402" data-end="3420">Exemple inicial:</p>
<p data-start="3402" data-end="3420" style="padding-left: 40px;"><code>#
    id,tipus,valor,unitat,instant</code><br><code>1,TEMPERATURA,36.7,ºC,2025-09-30
    10:15</code><br><code>2,POLS,72,bpm,2025-09-30
    10:16</code><br><code>3,SPO2,97,%,2025-09-30 10:17</code><br><code># aqui hi
    ha un comentari</code><br><code>4,TEMPERATURA,37.2,ºC,2025-09-30
    11:00</code><br><code>5,POLS,65,bpm,2025-09-30
    11:05</code><br><code>6,SPO2,95,%,2025-09-30
    11:10</code><br><code>7,TEMPERATURA,36.5,ºC,2025-09-30 12:20</code></p>
<h3 data-start="3707" data-end="3724">4) Lliurament</h3>
<p data-start="3725" data-end="3766"><strong data-start="3725"
    data-end="3739">Data límit</strong>: 1/03/2025 a les 23:55.</p>
<p data-start="3768" data-end="3789">Entrega un ZIP amb el projecte IntelliJ que
  contindrà, com a minim:</p>
<ol>
  <li data-start="3793" data-end="3845"> <code data-start="3815"
      data-end="3828">Mesura.java</code> i <code data-start="3831"
      data-end="3842">Main.java</code>.</li>
  <li data-start="3849" data-end="3897">Fitxer <code data-start="3856"
      data-end="3869">mesures.csv</code> (dades inicials + noves).</li>
  <li data-start="3901" data-end="3938">Diagrama UML de la classe <code
      data-start="3927" data-end="3935">Mesura</code>.</li>
  <li data-start="3942" data-end="4020">README amb breu explicació de decisions
    (estratègia ASCII, validacions, etc.).</li>
</ol>