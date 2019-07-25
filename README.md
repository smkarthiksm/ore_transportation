# Belt Trek

Human have colonized mars. For several centuries, water, food and minerals were transported from earth to mars. Given the protests on earth, while exploring to find a closer alternative, scientists have identified asteroids in belt which contains frozen water and other minerals. As a software consultant to the Outer Planet Alliance (OPA), your job is to build a system that would help them plan the mining to minimize trips.

Ship has containers which can contain resource of specific type. A container can contain only one resource at a time. Not all containers can carry all the resources. There are fluid containers which can store liquids. There are radiation containers which can carry radioactive resources. There are pressured containers which can store gases pressurized to reduce volume. There are pyrex containers which store acids. 


Some resources can be mixed to transform them to other forms for easy transportation. Some resources can be split up. The transformations can be done on either side of trip. Ship carry empty containers when going to asteroids. Due to safety considerations, few resources can't be transported together on the same trip. Containers can't be partially filled during transport, it is either empty or full.

Given possible resource types, resource transformations, container definitions, max containers allowed on ship, constraints on resource transport, resources on asteroid, and the need at Mars, chart out a plan to minimize trips, and print the trip details.


# Input

```
ore types
container definition
equations
max containers allowed on each trip
constraints at resource level
resources on asteroid
need 
```

# Output

```
Number of trips
--- // trip 1
ContainerName1-content1
ContainerName2-content2
--- // trip 2
ContainerName3-content1
ContainerName3-content2
...
```


# Input

```
5
SulfurResource-acidic
SO2Steam-gas
O2HotSteam-gas
Junk-solid
NitricResource-acidic
5
RC1-100-radioactive
LC1-50-liquid
GC1-100-gas
GC2-100-gas
AC1-100-acidic
1
SO2Steam=SulfurResource+O2HotSteam;70+30
2
1
NitricResource,O2HotSteam
3
SO2Steam
NitricResource
Junk
1
O2HotSteam-60
```

# Output

```
1
----
GC1-O2HotSteam
```
 
 ----
 
 # Input with hints
 
 ```
 18 //number of resourcess
 SulphateResouce-acidic
 UResource-radioactive
 WaterIce-solid
 WaterSlush-liquid
 LeadResource-solid
 ULeadSlush-liquid
 HePockets-gas
 ThResource-radioactive
 O2HotSteam-gas
 O2ColdSteam-gas
 GoldResouce-solid
 GoldLava-liquid
 GoldThSlush-liquid
 SO2Steam-gas
 NO2Steam-gas
 SulfurResource-acidic
 NitricResource-acidic
 Junk-solid
 4 // number of containers
 RC1-100-radioactive  // here RC1 is the container name, 100 capacity, radioactive is type
 LC1-50-liquid
 GC1-50-gas
 AC1-100-acidic
 7  // number of combinations
 SulphateResouce=SulfurResouce+WaterSlush;90+10 // SulphateResouce can be split to or combined from SulfurResouce (90%) , WaterSlush (10%)
 ULeadSlush=UResource+LeadResource+WaterSlush;60+30+10
 SO2Steam=SulfurResource+O2HotSteam;70+30
 WaterSlush=WaterIce+Junk;90+10
 GoldThSlush=GoldResouce+ThResource+WaterSlush;50+30+20
 GoldLava=GoldResouce+O2HotSteam;80+20
 NO2Steam=NitricResource+O2HotSteam;75+25
 2  // max containers allowed in ship
 4  // number of constraints
 ThResource,UResource // ThResource and UResource  can't be transported together
 GoldLava,SO2Steam   // GoldLava and SO2Steam can't be transported together
 NitricResource,O2HotSteam
 SulfurResource,ULeadSlush
 1   // number of resources on asteroid
 SulfurResource     // resources available on asteroid
 1   // number of needs
 SulfurResource-200  // need at mars
 ```
 
 # Output with hints
 
 ```
 2   // num of trips
 ---- // trip separator
 AC1-SulfurResource   // AC1 - container, SulfurResource - content
 ----
 AC1-SulfurResource
 ```

 
 ----
 
 

# Input

```
18
SulphateResouce-acidic
UResource-radioactive
WaterIce-solid
WaterSlush-liquid
LeadResource-solid
ULeadSlush-liquid
HePockets-gas
ThResource-radioactive
O2HotSteam-gas
O2ColdSteam-gas
GoldResouce-solid
GoldLava-liquid
GoldThSlush-liquid
SO2Steam-gas
NO2Steam-gas
SulfurResource-acidic
NitricResource-acidic
Junk-solid
4
RC1-100-radioactive
LC1-50-liquid
GC1-50-gas
AC1-100-acidic
7
SulphateResouce=SulfurResouce+WaterSlush;90+10
ULeadSlush=UResource+LeadResource+WaterSlush;60+30+10
SO2Steam=SulfurResource+O2HotSteam;70+30
WaterSlush=WaterIce+Junk;90+10
GoldThSlush=GoldResouce+ThResource+WaterSlush;50+30+20
GoldLava=GoldResouce+O2HotSteam;80+20
NO2Steam=NitricResource+O2HotSteam;75+25
2
4
ThResource,UResource
GoldLava,SO2Steam
NitricResource,O2HotSteam
SulfurResource,ULeadSlush
1
SulfurResource
1
SulfurResource-200
```

# Output

```
2
----
AC1-SulfurResource
----
AC1-SulfurResource
```


# Input

```
18
SulphateResouce-acidic
UResource-radioactive
WaterIce-solid
WaterSlush-liquid
LeadResource-solid
ULeadSlush-liquid
HePockets-gas
ThResource-radioactive
O2HotSteam-gas
O2ColdSteam-gas
GoldResouce-solid
GoldLava-liquid
GoldThSlush-liquid
SO2Steam-gas
NO2Steam-gas
SulfurResource-acidic
NitricResource-acidic
Junk-solid
3
RC1-100-radioactive
LC1-100-liquid
GC2-100-gas
7
SulphateResouce=SulfurResouce+WaterSlush;90+10
ULeadSlush=UResource+LeadResource+WaterSlush;60+30+10
SO2Steam=SulfurResource+O2HotSteam;70+30
WaterSlush=WaterIce+Junk;90+10
GoldThSlush=GoldResouce+ThResource+WaterSlush;50+30+20
GoldLava=GoldResouce+O2HotSteam;80+20
NO2Steam=NitricResource+O2HotSteam;75+25
2
2
ThResource,UResource
GoldLava,SO2Steam
4
SulfurResource
WaterIce
Junk
O2HotSteam
1
SulphateResouce-400
```

# Output

```
6
----
LC1-WaterSlush
GC2-SO2Steam
----
GC2-SO2Steam
----
GC2-SO2Steam
----
GC2-SO2Steam
----
GC2-SO2Steam
----
GC2-SO2Steam
```

# Input

```
18
SulphateResouce-acidic
UResource-radioactive
WaterIce-solid
WaterSlush-liquid
LeadResource-solid
ULeadSlush-liquid
HePockets-gas
ThResource-radioactive
O2HotSteam-gas
O2ColdSteam-gas
GoldResouce-solid
GoldLava-liquid
GoldThSlush-liquid
SO2Steam-gas
NO2Steam-gas
SulfurResource-acidic
NitricResource-acidic
Junk-solid
2
RC1-100-radioactive
LC1-100-liquid
7
SulphateResouce=SulfurResouce+WaterSlush;90+10
ULeadSlush=UResource+LeadResource+WaterSlush;60+30+10
SO2Steam=SulfurResource+O2HotSteam;70+30
WaterSlush=WaterIce+Junk;90+10
GoldThSlush=GoldResouce+ThResource+WaterSlush;50+30+20
GoldLava=GoldResouce+O2HotSteam;80+20
NO2Steam=NitricResource+O2HotSteam;75+25
1
GoldLava,UResource
4
NO2Steam
GoldLava
WaterSlush
UResource
2
UResource-100
GoldLava-100
```

# Output

```
2
----
RC1-UResource
----
LC1-GoldLava
```


## Before you submit

1. Make sure your code reflects the practices that you think are good
2. Please do not have the code in a public repo. 
3. If you have experience with unit tests or other testing frameworks, please demonstrate in your solution

We value a quality submission that takes time than a hasty submission that is done fast
