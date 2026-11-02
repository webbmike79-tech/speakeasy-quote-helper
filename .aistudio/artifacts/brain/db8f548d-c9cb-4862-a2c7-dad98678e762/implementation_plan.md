# Speakeasy Support — Customer Quote Helper

A mobile-friendly, single-page IT support quote estimator for **Speakeasy Support** (serving Stephenville, Millsap, Parker County, and West Fort Worth, TX). It combines a refined 1910s Chicago hard-boiled aesthetic in the header and brand framing with crystal-clear, zero-slang pricing estimates, recurring monthly plan comparisons, AI-assisted custom problem estimation, and direct contact actions.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following product and design choices have been confirmed from your answers and will guide the implementation:

- **Confirmed Decision 1 — Smart AI Estimation for "Something Else"**: Free-text problem descriptions in the "Something else" path will use **Firebase AI Logic (`gemini-3.8-flash`)** with structured JSON output to summarize the customer's issue back to them in plain, clear language and estimate a realistic hour range mapped strictly to the official hourly rates ($65/hr remote, $85/hr on-site with 1-hour minimum). A deterministic local fallback estimator is also built in so quotes still work instantaneously if offline.
- **Confirmed Decision 2 — Dark Speakeasy Palette with High-Contrast Quote Cards**: The interface uses a deep speakeasy charcoal backdrop (`#121316`) with aged brass/gold accents (`#D4AF37`) and pinstripe dividers for the header and selection controls, paired with high-contrast warm ledger cards (`#FAF6EE` with `#18181B` ink) for the quote breakdown so every dollar figure, policy, and disclaimer is effortless to read.
- **Confirmed Decision 3 — One-Tap Quote Sharing & Direct Contact Actions**: In addition to tap-to-call, text, email, and website buttons, the quote card includes one-tap actions to **Copy Quote Summary** to the clipboard or **Pre-fill a Text Message / Email** to `(239) 560-9061` / `spkezspprt@gmail.com` containing the customer's selected problem, service mode, and preliminary estimate.

---

## 1. Overview & Core Concept

- **What It Does**: Guides prospective customers through a fast, 3-step interactive quote builder on a single scrollable screen with large, accessible touch targets:
  1. **Pick the Problem**: Choose from the 10 supported categories (*Slow computer*, *Virus/malware cleanup*, *Wi-Fi/network problems*, *New computer or device setup*, *Printer problems*, *Smart home help*, *WordPress site work (create / migrate / customize)*, *Business IT support (ongoing)*, *PC build inquiry*, or *Something else*).
  2. **Pick Customer & Service Type**: Select **Home** or **Business**, then **Remote ($65/hr)** or **On-site visit ($85/hr, 1-hr min)**, plus an optional toggle for **Recurring / repeated issue** to immediately surface monthly plan savings math.
  3. **Review Preliminary Estimate & Reach Out**: Displays an itemized, crystal-clear preliminary estimate enforcing all pricing rules, deposit/cancellation policies, monthly plan math comparisons, Maverick Computers referral for PC builds, the exact word-for-word disclaimer, and direct contact/share buttons.
- **Target Audience / Persona**: Home users, small businesses, and doctor-owned medical practices across Millsap, Stephenville, Parker County, and West Fort Worth looking for transparent IT support pricing without creating an account or filling out a lead-capture form.
- **Key Value**: Instant, transparent cost expectations with zero login, zero data collection, and frictionless handoff to call, text, or email Speakeasy Support.

---

## 2. User Experience & Visual Design

- **Key User Flows**:
  - **Standard Problem Flow**: Customer taps a problem card (e.g., *Wi-Fi/network problems*), selects *Home* or *Business*, and chooses *Remote* or *On-site visit*. The live quote ledger updates immediately with the estimated hour range, hourly rate math, total preliminary range, policy notes (50% deposit for jobs over $500; <24h cancellation trip fee forfeiture on deposits), and the mandatory disclaimer.
  - **Recurring Issue / Business IT Math Flow**: When *Business IT support (ongoing)* is selected—or when the customer marks an issue as recurring/repeated—a highlighted **"Smarter Deal: Monthly Plan Comparison"** callout appears inside the quote card. It shows the exact side-by-side math comparing pay-per-incident hourly costs against the matching monthly plan (**Home $49/mo**, **Small Business $199/mo**, or **Premium $349/mo** for multi-provider/doctor-owned practices or heavy ongoing needs).
  - **PC Build Inquiry Flow**: Selecting *PC build inquiry* displays a dedicated advisory note stating clearly that Speakeasy Support does not stock hardware parts, provides rough consultation/assembly labor and typical build tier cost ideas for reference only, and refers the customer to **Maverick Computers** for parts and custom system builds.
  - **"Something Else" Custom Problem Flow**: Selecting *Something else* reveals a multi-line text input box (*"Describe your problem in your own words"*) and an **"Analyze & Estimate"** button. Gemini summarizes what the customer typed in clear, plain English, estimates a realistic hour range (and flags if it sounds recurring or exceeds $500), and computes the exact dollar range using strictly the $65/hr Remote or $85/hr On-site rate alongside the mandatory disclaimer.
  - **Contact & Handoff Flow**: At the bottom of the quote, the **Speakeasy Support Contact Card** offers big tappable buttons to Call `(239) 560-9061`, Text `(239) 560-9061` (pre-filled with the quote summary), Email `spkezspprt@gmail.com` (pre-filled subject and body), Copy Quote to Clipboard, or open `https://webbmike79-tech.github.io/speakeasy-support/`.

- **Visual Identity & Theme**:
  - *Aesthetic Direction*: **1910s Chicago Speakeasy / Hard-Boiled Editorial** — sharp, stylish, and authoritative in the header and section framing ("No nonsense. Straight numbers. Fixed right."), transitioning into an ultra-legible, zero-slang financial ledger for the quote itself.
  - *Color Palette & Mood*:
    - `--background`: `#121316` (Obsidian Speakeasy Charcoal)
    - `--surface`: `#1C1E24` (Smoky Slate Card Surface for interactive selection tiles)
    - `--primary`: `#D4AF37` (Aged Chicago Brass / Gold for active borders, badges, and primary CTAs)
    - `--secondary`: `#8C6D2E` (Muted Antique Bronze for subtle Art Deco / 1910s rule lines)
    - `--quote-card-bg`: `#FAF6EE` (High-Contrast Warm Ledger Paper for the Quote Breakdown card)
    - `--quote-card-ink`: `#141416` (Deep Printer's Ink for crystal-clear price and policy typography)
  - *Typography & Hierarchy*:
    - **Display / Header Font**: *Playfair Display* (bundled locally) for the "SPEAKEASY SUPPORT" masthead, tagline, and section headers.
    - **Body & UI Font**: *Plus Jakarta Sans* or *Open Sans* (bundled locally) for problem buttons, policy bullets, and plain-English explanations.
    - **Monospace / Price Figures**: *Roboto Mono* (bundled locally) for hourly rates, math comparisons, and total dollar ranges.
  - *Component Styling & Layout*: Single-page vertical scroll layout adaptive to both phones (`Compact`) and tablets/foldables (`Medium`/`Expanded` two-column split with selection controls on the left and sticky live quote + contact card on the right). Every interactive button exceeds `56.dp` height with bold borders, clear selection checkmarks, and tactile ripple feedback.

- **Interactive Feedback & Motion**:
  - Smooth spring transitions (`AnimatedVisibility`) when revealing the "Something else" description box, the recurring monthly plan math comparison, or the >$500 deposit notice.
  - Instant clipboard confirmation snackbar when copying a quote summary.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Strict Pricing Guardrails with Deterministic Math Calculation**
  - *Chosen Approach*: All dollar amounts are computed strictly in Kotlin domain logic using only the authorized pricing constants (`$65/hr` Remote, `$85/hr` On-site with 1-hr minimum, `$49/mo` Home, `$199/mo` Small Business, `$349/mo` Premium, 50% deposit over `$500`, <24h cancellation trip fee rule). Even when Gemini analyzes a custom "Something else" problem, Gemini only returns the plain-English problem summary, estimated hour range (`minHours`, `maxHours`), and whether the issue appears recurring—Kotlin multiplies those hours by `$65` or `$85` so the AI can never invent unauthorized prices, fees, or discounts.
  - *Why*: Guarantees 100% compliance with the rule to never invent prices, services, or discounts and ensures the exact word-for-word disclaimer (`"Preliminary estimate — final pricing confirmed by Mike."`) appears on every single quote.
  - *Alternatives Considered*: Letting the LLM format the dollar strings directly, which carries a risk of hallucinating rates or altering the mandatory disclaimer wording.

- **Decision 2: Stateless, Zero-Data-Collection Architecture**
  - *Chosen Approach*: All quote state lives strictly in memory inside the `QuoteViewModel` during the user's session. No login, no accounts, no database, and no analytics tracking are included.
  - *Why*: Directly fulfills the privacy and simplicity rules ("No login, no account, no data collection — it is a quote helper, nothing is saved").
  - *Alternatives Considered*: Local persistence or cloud history, which were rejected to respect the strict zero-collection requirement.

---

## 4. Technical Architecture & Data Strategy *(Technical Reference)*

- **Architecture & Component Diagram**:

```
┌───────────────────────────────────────────────────────────────────────────┐
│                     MainActivity (Edge-to-Edge Compose)                   │
│            App Check Initialization (configureAppCheck on launch)         │
└─────────────────────────────────────┬─────────────────────────────────────┘
                                      │
                                      ▼
┌───────────────────────────────────────────────────────────────────────────┐
│                     QuoteHelperScreen (Adaptive Layout)                   │
│  ┌──────────────────────────────────┐  ┌───────────────────────────────┐  │
│  │  1. Speakeasy Masthead Banner    │  │ 4. High-Contrast Quote Ledger │  │
│  │     (Millsap -> W. Fort Worth)   │  │    • Hourly Rate & Range Math │  │
│  ├──────────────────────────────────┤  │    • >$500 Deposit & <24h Rule│  │
│  │  2. Problem Selector Grid (10)   │  │    • Monthly Plan Math Box    │  │
│  │     + "Something Else" Input Box │  │    • Maverick PC Referral     │  │
│  ├──────────────────────────────────┤  │    • Mandatory Mike Disclaimer│  │
│  │  3. Customer & Visit Toggles     │  ├───────────────────────────────┤  │
│  │     • Home vs. Business          │  │ 5. Speakeasy Contact Card     │  │
│  │     • Remote ($65) vs On-Site($85│  │    • Pre-fill SMS / Email     │  │
│  │     • Recurring Issue Toggle     │  │    • Call / Copy / Website    │  │
│  └──────────────────────────────────┘  └───────────────────────────────┘  │
└──────────────────┬──────────────────────────────────────┬─────────────────┘
                   │ User Actions                         │ StateFlow<QuoteUiState>
                   ▼                                      │
┌─────────────────────────────────────────────────────────┴─────────────────┐
│                              QuoteViewModel                               │
│  ┌──────────────────────────────────┐  ┌───────────────────────────────┐  │
│  │   StrictPricingCalculator        │  │   CustomProblemAiEstimator    │  │
│  │   • Remote: $65/hr               │  │   • Firebase.ai               │  │
│  │   • On-site: $85/hr (1h min)     │  │     ("gemini-3.8-flash")      │  │
│  │   • Plans: $49 / $199 / $349     │  │   • Structured Schema.obj:    │  │
│  │   • >$500 -> 50% deposit math    │  │     summary, minHrs, maxHrs,  │  │
│  │   • <24h cancel forfeits trip fee│  │     isRecurring, isPcBuild    │  │
│  └──────────────────────────────────┘  └───────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────────────┘
```

- **Data Model & State**:
  - `ProblemCategory` (enum of the 10 exact problem options, each with default estimated hour ranges, default recurring flag, and plain-English scope notes).
  - `CustomerType`: `HOME` (`$49/mo` plan match) or `BUSINESS` (`$199/mo` Small Business & `$349/mo` Premium plan match for larger/doctor-owned practices).
  - `ServiceMode`: `REMOTE` (`$65/hour`) or `ON_SITE` (`$85/hour, 1-hour minimum`).
  - `QuoteCalculation`: Holds the computed `minHours`, `maxHours`, `hourlyRate`, `minTotal`, `maxTotal`, `requiresFiftyPercentDeposit` (true when estimate exceeds `$500`), `depositAmountRange`, `monthlyPlanComparison` (shows exact monthly vs. hourly break-even math when recurring or Business IT is selected), `isPcBuildReferral` (Maverick Computers referral), and `exactDisclaimer = "Preliminary estimate — final pricing confirmed by Mike."`.

- **Interactive Component & State Mapping**:
  - **Problem Selection Tiles**: Tapping any of the 10 problem buttons updates `selectedProblem` in `QuoteViewModel` and immediately recalculates `QuoteCalculation`. If *Business IT support (ongoing)* is chosen, `customerType` automatically defaults to `BUSINESS` and activates the monthly plan comparison. If *Something else* is chosen, the custom text field expands into view.
  - **Customer & Service Mode Toggles**: Tapping *Home* / *Business* or *Remote ($65/hr)* / *On-site ($85/hr)* immediately updates the hourly multiplier and monthly plan tier math. A *"Happens repeatedly / ongoing need?"* switch lets customers toggle the monthly plan math comparison on any problem category.
  - **"Something Else" AI Analyzer**: Typing a custom problem description and tapping **"Get Preliminary Estimate"** triggers `analyzeCustomProblem()` in `QuoteViewModel` with a 60-second coroutine timeout. While loading, a progress indicator appears; on completion, the structured JSON response populates the customer summary and hour range, which `StrictPricingCalculator` converts into exact dollar figures with the mandatory disclaimer. If the user is offline or AI fails, a deterministic keyword-based fallback estimator immediately provides a safe preliminary hour range and summary so the customer is never blocked.
  - **Contact & Share Handlers**:
    - *Call*: Launches `Intent(Intent.ACTION_DIAL, Uri.parse("tel:2395609061"))`.
    - *Text Quote*: Launches `Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:2395609061"))` with `"sms_body"` pre-populated with the formatted quote summary and disclaimer.
    - *Email Quote*: Launches `Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:spkezspprt@gmail.com"))` with subject `"Speakeasy Support Quote Inquiry"` and body pre-populated with the quote summary.
    - *Copy Quote*: Copies the formatted summary to `ClipboardManager` and triggers a confirmation snackbar.
    - *Website*: Launches `Intent(Intent.ACTION_VIEW, Uri.parse("https://webbmike79-tech.github.io/speakeasy-support/"))`.
